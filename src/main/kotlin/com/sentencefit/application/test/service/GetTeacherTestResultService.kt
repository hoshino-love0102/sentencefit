package com.sentencefit.application.test.service

import com.sentencefit.application.test.dto.TeacherTestQuestionResultResponse
import com.sentencefit.application.test.dto.TeacherTestResultResponse
import com.sentencefit.application.test.dto.TeacherTestStepResultResponse
import com.sentencefit.application.test.port.`in`.GetTeacherTestResultUseCase
import com.sentencefit.application.test.port.out.LoadStudentTestPort
import com.sentencefit.application.test.port.out.LoadStudentTestQuestionPort
import com.sentencefit.application.test.port.out.LoadStudentTestStepResultPort
import com.sentencefit.application.test.port.out.LoadTeacherTestAccessPort
import com.sentencefit.domain.test.exception.TeacherTestErrorCode
import com.sentencefit.domain.test.exception.TeacherTestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetTeacherTestResultService(
    private val loadStudentTestPort: LoadStudentTestPort,
    private val loadStudentTestQuestionPort: LoadStudentTestQuestionPort,
    private val loadStudentTestStepResultPort: LoadStudentTestStepResultPort,
    private val loadTeacherTestAccessPort: LoadTeacherTestAccessPort,
) : GetTeacherTestResultUseCase {

    @Transactional(readOnly = true)
    override fun getResult(
        teacherId: Long,
        testId: Long,
    ): TeacherTestResultResponse {
        val test = loadStudentTestPort.loadTestById(testId)
            ?: throw TeacherTestException(TeacherTestErrorCode.TEST_NOT_FOUND)

        if (!loadTeacherTestAccessPort.canAccessTest(teacherId, testId)) {
            throw TeacherTestException(TeacherTestErrorCode.TEST_NOT_ACCESSIBLE)
        }

        val questions = loadStudentTestQuestionPort.loadQuestionsByTestId(testId)
        val stepResults = loadStudentTestStepResultPort.loadStepResultsByTestId(testId)

        val stepResultsByQuestionId = stepResults.groupBy { it.questionId }
        val latestByQuestionId = stepResultsByQuestionId
            .mapValues { (_, results) -> results.maxByOrNull { it.stepNo } }

        val questionResults = questions.map { question ->
            val questionId = question.id
                ?: throw TeacherTestException(TeacherTestErrorCode.TEST_NOT_FOUND)

            val questionStepResults = stepResultsByQuestionId[questionId]
                .orEmpty()
                .sortedBy { it.stepNo }

            val latestResult = latestByQuestionId[questionId]

            TeacherTestQuestionResultResponse(
                questionId = questionId,
                questionNo = question.questionNo,
                koreanText = question.koreanText,
                grammarPoint = question.grammarPoint,
                isCorrect = latestResult?.isCorrect ?: false,
                correctAnswer = latestResult?.correctAnswer ?: question.englishText,
                studentAnswer = latestResult?.submittedAnswerText
                    ?: latestResult?.submittedAnswerTokens?.joinToString(" "),
                errorType = latestResult?.errorType?.name,
                explanation = latestResult?.explanation,
                canRetest = latestResult?.canRetest ?: false,
                steps = questionStepResults.map { step ->
                    TeacherTestStepResultResponse(
                        stepNo = step.stepNo,
                        stepType = step.stepType.name,
                        answerType = step.answerType.name,
                        studentAnswer = step.submittedAnswerText
                            ?: step.submittedAnswerTokens?.joinToString(" "),
                        correctAnswer = step.correctAnswer,
                        isCorrect = step.isCorrect,
                        explanation = step.explanation,
                        errorType = step.errorType?.name,
                        usedAi = step.usedAi,
                        canRetest = step.canRetest,
                    )
                }
            )
        }

        return TeacherTestResultResponse(
            testId = test.id ?: throw TeacherTestException(TeacherTestErrorCode.TEST_NOT_FOUND),
            studentId = test.studentId,
            setId = test.setId,
            status = test.status.name,
            mode = test.mode.name,
            score = test.score,
            correctCount = test.correctCount,
            wrongCount = test.wrongCount,
            totalCount = test.totalQuestions,
            retestCount = test.retestCount,
            currentQuestionNo = test.currentQuestionNo,
            questions = questionResults,
        )
    }
}