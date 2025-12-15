package com.sentencefit.adapter.`in`.web

import com.sentencefit.adapter.`in`.security.CustomUserPrincipal
import com.sentencefit.application.test.dto.StartStudentTestRequest
import com.sentencefit.application.test.dto.StartStudentTestResponse
import com.sentencefit.application.test.dto.StudentTestQuestionResponse
import com.sentencefit.application.test.dto.StudentTestResultResponse
import com.sentencefit.application.test.dto.SubmitStudentAnswerRequest
import com.sentencefit.application.test.dto.SubmitStudentAnswerResponse
import com.sentencefit.application.test.port.`in`.GetStudentTestQuestionUseCase
import com.sentencefit.application.test.port.`in`.GetStudentTestResultUseCase
import com.sentencefit.application.test.port.`in`.StartStudentTestUseCase
import com.sentencefit.application.test.port.`in`.SubmitStudentAnswerUseCase
import com.sentencefit.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student")
class StudentTestController(
    private val startStudentTestUseCase: StartStudentTestUseCase,
    private val getStudentTestQuestionUseCase: GetStudentTestQuestionUseCase,
    private val submitStudentAnswerUseCase: SubmitStudentAnswerUseCase,
    private val getStudentTestResultUseCase: GetStudentTestResultUseCase,
) {

    @PostMapping("/sets/{setId}/tests/start")
    fun start(
        @PathVariable setId: Long,
        @Valid @RequestBody request: StartStudentTestRequest,
        @AuthenticationPrincipal principal: CustomUserPrincipal,
    ): ApiResponse<StartStudentTestResponse> {
        val data = startStudentTestUseCase.start(
            studentId = principal.userId,
            setId = setId,
            request = request,
        )
        return ApiResponse.success(data, "테스트 시작 성공")
    }

    @GetMapping("/tests/{testId}/questions/{no}")
    fun getQuestion(
        @PathVariable testId: Long,
        @PathVariable no: Int,
        @AuthenticationPrincipal principal: CustomUserPrincipal,
    ): ApiResponse<StudentTestQuestionResponse> {
        val data = getStudentTestQuestionUseCase.getQuestion(
            studentId = principal.userId,
            testId = testId,
            no = no,
        )
        return ApiResponse.success(data, "문제 조회 성공")
    }

    @PostMapping("/tests/{testId}/answers")
    fun submitAnswer(
        @PathVariable testId: Long,
        @Valid @RequestBody request: SubmitStudentAnswerRequest,
        @AuthenticationPrincipal principal: CustomUserPrincipal,
    ): ApiResponse<SubmitStudentAnswerResponse> {
        val data = submitStudentAnswerUseCase.submit(
            studentId = principal.userId,
            testId = testId,
            request = request,
        )
        return ApiResponse.success(data, "채점 완료")
    }

    @GetMapping("/tests/{testId}")
    fun getResult(
        @PathVariable testId: Long,
        @AuthenticationPrincipal principal: CustomUserPrincipal,
    ): ApiResponse<StudentTestResultResponse> {
        val data = getStudentTestResultUseCase.getResult(
            studentId = principal.userId,
            testId = testId,
        )
        return ApiResponse.success(data, "테스트 결과 조회 성공")
    }
}