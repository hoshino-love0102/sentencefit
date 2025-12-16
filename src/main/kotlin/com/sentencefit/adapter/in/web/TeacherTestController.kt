package com.sentencefit.adapter.`in`.web

import com.sentencefit.adapter.`in`.security.CustomUserPrincipal
import com.sentencefit.application.test.dto.TeacherTestResultResponse
import com.sentencefit.application.test.port.`in`.GetTeacherTestResultUseCase
import com.sentencefit.common.response.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/teacher")
class TeacherTestController(
    private val getTeacherTestResultUseCase: GetTeacherTestResultUseCase,
) {

    @GetMapping("/tests/{testId}")
    fun getResult(
        @PathVariable testId: Long,
        @AuthenticationPrincipal principal: CustomUserPrincipal,
    ): ApiResponse<TeacherTestResultResponse> {
        val data = getTeacherTestResultUseCase.getResult(
            teacherId = principal.userId,
            testId = testId,
        )
        return ApiResponse.success(data, "테스트 결과 조회 성공")
    }
}