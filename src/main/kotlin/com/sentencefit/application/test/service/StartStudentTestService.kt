package com.sentencefit.application.test.service

import com.sentencefit.application.test.dto.StartStudentTestRequest
import com.sentencefit.application.test.dto.StartStudentTestResponse
import com.sentencefit.application.test.port.`in`.StartStudentTestUseCase
import com.sentencefit.application.test.port.out.LoadSetForStudentTestPort
import com.sentencefit.application.test.port.out.LoadStudentMembershipPort
import com.sentencefit.application.test.port.out.SaveStudentTestPort
import com.sentencefit.application.test.port.out.SaveStudentTestQuestionPort
import com.sentencefit.domain.test.exception.StudentTestErrorCode
import com.sentencefit.domain.test.exception.StudentTestException
import com.sentencefit.domain.test.model.StudentTest
import com.sentencefit.domain.test.model.StudentTestQuestion
import com.sentencefit.domain.test.model.TestMode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StartStudentTestService(
    private val loadSetForStudentTestPort: LoadSetForStudentTestPort,
    private val loadStudentMembershipPort: LoadStudentMembershipPort,
    private val saveStudentTestPort: SaveStudentTestPort,
    private val saveStudentTestQuestionPort: SaveStudentTestQuestionPort,
) : StartStudentTestUseCase {

    @Transactional
    override fun start(
        studentId: Long,
        setId: Long,
        request: StartStudentTestRequest,
    ): StartStudentTestResponse {
        val set = loadSetForStudentTestPort.loadSet(setId)
            ?: throw StudentTestException(StudentTestErrorCode.SET_NOT_FOUND)

        if (!set.isPublished) {
            throw StudentTestException(
                StudentTestErrorCode.SET_NOT_ACCESSIBLE,
                "공개되지 않은 세트입니다."
            )
        }

        val hasMembership = loadStudentMembershipPort.existsActiveMembership(
            classId = set.classId,
            studentId = studentId,
        )
        if (!hasMembership) {
            throw StudentTestException(StudentTestErrorCode.SET_NOT_ACCESSIBLE)
        }

        val sentences = loadSetForStudentTestPort.loadSentences(setId)
            .sortedBy { it.orderNo }

        if (sentences.isEmpty()) {
            throw StudentTestException(StudentTestErrorCode.TEST_SENTENCE_EMPTY)
        }

        val mode = try {
            TestMode.from(request.mode)
        } catch (_: IllegalArgumentException) {
            throw StudentTestException(
                StudentTestErrorCode.SET_NOT_ACCESSIBLE,
                "유효하지 않은 mode 입니다."
            )
        }

        val savedTest = saveStudentTestPort.saveTest(
            StudentTest(
                studentId = studentId,
                setId = setId,
                mode = mode,
                totalQuestions = sentences.size,
            )
        )

        val testId = savedTest.id
            ?: throw IllegalStateException("저장된 테스트 ID가 없습니다.")

        val questions = sentences.mapIndexed { index, sentence ->
            StudentTestQuestion(
                testId = testId,
                sentenceId = sentence.sentenceId,
                questionNo = index + 1,
                englishText = sentence.englishText,
                koreanText = sentence.koreanText,
                grammarPoint = sentence.grammarPoint,
                tokens = sentence.englishText
                    .replace(".", " .")
                    .replace(",", " ,")
                    .trim()
                    .split(Regex("\\s+"))
                    .filter { it.isNotBlank() },
            )
        }

        saveStudentTestQuestionPort.saveQuestions(questions)

        return StartStudentTestResponse(
            testId = testId,
            totalQuestions = savedTest.totalQuestions,
            currentQuestionNo = savedTest.currentQuestionNo,
            status = savedTest.status.name,
        )
    }
}