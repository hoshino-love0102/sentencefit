package com.sentencefit.application.test.service

import com.sentencefit.application.test.dto.StudentTestQuestionResultResponse
import com.sentencefit.application.test.dto.StudentTestResultResponse
import com.sentencefit.application.test.port.`in`.GetStudentTestResultUseCase
import com.sentencefit.application.test.port.out.LoadStudentTestPort
import com.sentencefit.application.test.port.out.LoadStudentTestQuestionPort
import com.sentencefit.application.test.port.out.LoadStudentTestStepResultPort
import com.sentencefit.domain.test.exception.StudentTestErrorCode
import com.sentencefit.domain.test.exception.StudentTestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetStudentTestResultService(
    private val loadStudentTestPort: LoadStudentTestPort,
    private val loadStudentTestQuestionPort: LoadStudentTestQuestionPort,
    private val loadStudentTestStepResultPort: LoadStudentTestStepResultPort,
) : GetStudentTestResultUseCase {

    @Transactional(readOnly = true)
    override fun getResult(
        studentId: Long,
        testId: Long,
    ): StudentTestResultResponse {
        val test = loadStudentTestPort.loadTestById(testId)
            ?: throw StudentTestException(StudentTestErrorCode.TEST_NOT_FOUND)

        if (test.studentId != studentId) {
            throw StudentTestException(StudentTestErrorCode.TEST_NOT_ACCESSIBLE)
        }

        val questions = loadStudentTestQuestionPort.loadQuestionsByTestId(testId)
        val stepResults = loadStudentTestStepResultPort.loadStepResultsByTestId(testId)

        val latestByQuestionId = stepResults
            .groupBy { it.questionId }
            .mapValues { (_, results) -> results.maxByOrNull { it.stepNo }!! }

        val questionResults = questions.map { question ->
            val questionId = question.id
                ?: throw StudentTestException(StudentTestErrorCode.TEST_QUESTION_NOT_FOUND)

            val result = latestByQuestionId[questionId]

            StudentTestQuestionResultResponse(
                questionId = questionId,
                questionNo = question.questionNo,
                isCorrect = result?.isCorrect ?: false,
                correctAnswer = result?.correctAnswer ?: question.englishText,
                studentAnswer = result?.submittedAnswerText
                    ?: result?.submittedAnswerTokens?.joinToString(" "),
                errorType = result?.errorType?.name,
                explanation = result?.explanation,
                canRetest = result?.canRetest ?: false,
            )
        }

        return StudentTestResultResponse(
            testId = test.id ?: throw StudentTestException(StudentTestErrorCode.TEST_NOT_FOUND),
            setId = test.setId,
            status = test.status.name,
            score = test.score,
            correctCount = test.correctCount,
            wrongCount = test.wrongCount,
            totalCount = test.totalQuestions,
            retestCount = test.retestCount,
            questions = questionResults,
        )
    }
}