package com.sentencefit.application.test.service

import com.sentencefit.application.test.dto.StudentTestQuestionResponse
import com.sentencefit.application.test.port.`in`.GetStudentTestQuestionUseCase
import com.sentencefit.application.test.port.out.LoadStudentTestPort
import com.sentencefit.application.test.port.out.LoadStudentTestQuestionPort
import com.sentencefit.application.test.port.out.LoadStudentTestStepResultPort
import com.sentencefit.domain.test.exception.StudentTestErrorCode
import com.sentencefit.domain.test.exception.StudentTestException
import com.sentencefit.domain.test.policy.StudentTestStepPolicy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetStudentTestQuestionService(
    private val loadStudentTestPort: LoadStudentTestPort,
    private val loadStudentTestQuestionPort: LoadStudentTestQuestionPort,
    private val loadStudentTestStepResultPort: LoadStudentTestStepResultPort,
) : GetStudentTestQuestionUseCase {

    @Transactional(readOnly = true)
    override fun getQuestion(
        studentId: Long,
        testId: Long,
        no: Int,
    ): StudentTestQuestionResponse {
        val test = loadStudentTestPort.loadTestById(testId)
            ?: throw StudentTestException(StudentTestErrorCode.TEST_NOT_FOUND)

        if (test.studentId != studentId) {
            throw StudentTestException(StudentTestErrorCode.TEST_NOT_ACCESSIBLE)
        }

        val question = loadStudentTestQuestionPort.loadQuestionByTestIdAndQuestionNo(testId, no)
            ?: throw StudentTestException(StudentTestErrorCode.TEST_QUESTION_NOT_FOUND)

        val questionId = question.id
            ?: throw StudentTestException(StudentTestErrorCode.TEST_QUESTION_NOT_FOUND)

        val stepResults = loadStudentTestStepResultPort.loadStepResultsByQuestionId(questionId)
        val currentStepNo = (1..3).firstOrNull { stepNo ->
            stepResults.none { it.stepNo == stepNo }
        } ?: 3

        return StudentTestQuestionResponse(
            questionId = questionId,
            questionNo = question.questionNo,
            stepNo = currentStepNo,
            stepType = StudentTestStepPolicy.getStepType(currentStepNo).name,
            answerType = StudentTestStepPolicy.getAnswerType(currentStepNo).name,
            koreanText = question.koreanText,
            grammarPoint = question.grammarPoint,
            tokens = if (currentStepNo == 1 || currentStepNo == 2) question.tokens else null,
            isLastStep = StudentTestStepPolicy.isLastStep(currentStepNo),
            submitted = stepResults.any { it.stepNo == currentStepNo },
        )
    }
}