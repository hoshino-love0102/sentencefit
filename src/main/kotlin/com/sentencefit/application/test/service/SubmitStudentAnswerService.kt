package com.sentencefit.application.test.service

import com.fasterxml.jackson.databind.JsonNode
import com.sentencefit.application.test.dto.SubmitStudentAnswerRequest
import com.sentencefit.application.test.dto.SubmitStudentAnswerResponse
import com.sentencefit.application.test.port.`in`.SubmitStudentAnswerUseCase
import com.sentencefit.application.test.port.out.AiTestGradingCommand
import com.sentencefit.application.test.port.out.AiTestGradingPort
import com.sentencefit.application.test.port.out.LoadStudentTestPort
import com.sentencefit.application.test.port.out.LoadStudentTestQuestionPort
import com.sentencefit.application.test.port.out.LoadStudentTestStepResultPort
import com.sentencefit.application.test.port.out.SaveStudentTestPort
import com.sentencefit.application.test.port.out.SaveStudentTestStepResultPort
import com.sentencefit.domain.test.exception.StudentTestErrorCode
import com.sentencefit.domain.test.exception.StudentTestException
import com.sentencefit.domain.test.model.AnswerType
import com.sentencefit.domain.test.model.StudentTestStepResult
import com.sentencefit.domain.test.model.TestStatus
import com.sentencefit.domain.test.policy.StudentTestStepPolicy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubmitStudentAnswerService(
    private val loadStudentTestPort: LoadStudentTestPort,
    private val saveStudentTestPort: SaveStudentTestPort,
    private val loadStudentTestQuestionPort: LoadStudentTestQuestionPort,
    private val loadStudentTestStepResultPort: LoadStudentTestStepResultPort,
    private val saveStudentTestStepResultPort: SaveStudentTestStepResultPort,
    private val aiTestGradingPort: AiTestGradingPort,
) : SubmitStudentAnswerUseCase {

    @Transactional
    override fun submit(
        studentId: Long,
        testId: Long,
        request: SubmitStudentAnswerRequest,
    ): SubmitStudentAnswerResponse {
        val test = loadStudentTestPort.loadTestById(testId)
            ?: throw StudentTestException(StudentTestErrorCode.TEST_NOT_FOUND)

        if (test.studentId != studentId) {
            throw StudentTestException(StudentTestErrorCode.TEST_NOT_ACCESSIBLE)
        }

        if (test.status == TestStatus.COMPLETED) {
            throw StudentTestException(StudentTestErrorCode.TEST_ALREADY_COMPLETED)
        }

        StudentTestStepPolicy.validateStepNo(request.stepNo)

        val answerType = try {
            AnswerType.from(request.answerType)
        } catch (_: IllegalArgumentException) {
            throw StudentTestException(
                StudentTestErrorCode.INVALID_STEP_NO,
                "유효하지 않은 answerType 입니다."
            )
        }

        val expectedAnswerType = StudentTestStepPolicy.getAnswerType(request.stepNo)
        if (answerType != expectedAnswerType) {
            throw StudentTestException(
                StudentTestErrorCode.INVALID_STEP_NO,
                "해당 단계의 answerType이 올바르지 않습니다."
            )
        }

        val question = loadStudentTestQuestionPort.loadQuestionById(request.questionId)
            ?: throw StudentTestException(StudentTestErrorCode.TEST_QUESTION_NOT_FOUND)

        val questionId = question.id
            ?: throw StudentTestException(StudentTestErrorCode.TEST_QUESTION_NOT_FOUND)

        if (question.testId != testId) {
            throw StudentTestException(StudentTestErrorCode.TEST_NOT_ACCESSIBLE)
        }

        val existing = loadStudentTestStepResultPort
            .loadStepResultByQuestionIdAndStepNo(questionId, request.stepNo)

        if (existing != null) {
            throw StudentTestException(StudentTestErrorCode.STEP_ALREADY_SUBMITTED)
        }

        val submittedText = extractSubmittedText(answerType, request.answer)
        val submittedTokens = extractSubmittedTokens(answerType, request.answer)
        val correctAnswer = question.englishText

        val gradingResult = try {
            aiTestGradingPort.gradeAll(
                listOf(
                    AiTestGradingCommand(
                        questionId = questionId,
                        questionNo = question.questionNo,
                        stepNo = request.stepNo,
                        stepType = StudentTestStepPolicy.getStepType(request.stepNo),
                        answerType = answerType,
                        englishText = question.englishText,
                        correctAnswer = correctAnswer,
                        submittedAnswerText = submittedText,
                        submittedAnswerTokens = submittedTokens,
                        koreanText = question.koreanText,
                        grammarPoint = question.grammarPoint,
                    )
                )
            ).firstOrNull() ?: throw StudentTestException(
                StudentTestErrorCode.AI_GRADING_FAILED,
                "AI 채점 결과가 비어 있습니다."
            )
        } catch (e: Exception) {
            throw StudentTestException(
                StudentTestErrorCode.AI_GRADING_FAILED,
                e.message ?: "AI 채점 실패"
            )
        }

        saveStudentTestStepResultPort.saveStepResult(
            StudentTestStepResult(
                testId = testId,
                questionId = questionId,
                stepNo = request.stepNo,
                stepType = StudentTestStepPolicy.getStepType(request.stepNo),
                answerType = answerType,
                submittedAnswerText = submittedText,
                submittedAnswerTokens = submittedTokens,
                isCorrect = gradingResult.isCorrect,
                correctAnswer = gradingResult.correctAnswer,
                explanation = gradingResult.explanation,
                errorType = gradingResult.errorType,
                usedAi = gradingResult.usedAi,
                canRetest = gradingResult.canRetest,
            )
        )

        val allResults = loadStudentTestStepResultPort.loadStepResultsByTestId(testId)
        val allQuestions = loadStudentTestQuestionPort.loadQuestionsByTestId(testId)

        val questionIds = allQuestions.mapNotNull { it.id }.toSet()
        val completedQuestionIds = allResults
            .groupBy { it.questionId }
            .filterValues { results -> results.any { it.stepNo == 3 } }
            .keys

        val isTestCompleted = questionIds.isNotEmpty() && questionIds == completedQuestionIds
        val nextStepNo = StudentTestStepPolicy.nextStepNo(request.stepNo)
        val nextQuestionNo = if (nextStepNo == null) question.questionNo + 1 else question.questionNo

        var finalStatus = test.status
        if (isTestCompleted) {
            val finalPerQuestion = allResults
                .groupBy { it.questionId }
                .mapNotNull { (_, results) -> results.maxByOrNull { it.stepNo } }

            val correctCount = finalPerQuestion.count { it.isCorrect }
            val wrongCount = allQuestions.size - correctCount
            val retestCount = finalPerQuestion.count { it.canRetest }
            val score = if (allQuestions.isEmpty()) 0 else (correctCount * 100) / allQuestions.size

            saveStudentTestPort.saveTest(
                test.complete(
                    score = score,
                    correctCount = correctCount,
                    wrongCount = wrongCount,
                    retestCount = retestCount,
                )
            )
            finalStatus = TestStatus.COMPLETED
        } else {
            val moveQuestionNo = nextQuestionNo.coerceAtMost(test.totalQuestions)
            saveStudentTestPort.saveTest(test.moveCurrentQuestion(moveQuestionNo))
        }

        return SubmitStudentAnswerResponse(
            isCorrect = gradingResult.isCorrect,
            correctAnswer = gradingResult.correctAnswer,
            explanation = gradingResult.explanation,
            errorType = gradingResult.errorType?.name,
            nextStepNo = if (isTestCompleted) null else nextStepNo,
            nextQuestionNo = if (isTestCompleted || nextQuestionNo > test.totalQuestions) null else nextQuestionNo,
            usedAi = gradingResult.usedAi,
            canRetest = gradingResult.canRetest,
            testStatus = finalStatus.name,
            isTestCompleted = isTestCompleted,
        )
    }

    private fun extractSubmittedText(answerType: AnswerType, answer: JsonNode): String? =
        when (answerType) {
            AnswerType.TEXT -> if (answer.isTextual) answer.asText() else null
            AnswerType.TOKEN_ARRAY -> if (answer.isArray) answer.map { it.asText() }.joinToString(" ") else null
        }

    private fun extractSubmittedTokens(answerType: AnswerType, answer: JsonNode): List<String>? =
        when (answerType) {
            AnswerType.TEXT -> null
            AnswerType.TOKEN_ARRAY -> if (answer.isArray) answer.map { it.asText() } else null
        }
}