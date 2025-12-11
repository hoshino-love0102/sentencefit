package com.sentencefit.adapter.`in`.web

import com.sentencefit.adapter.`in`.security.CustomUserPrincipal
import com.sentencefit.application.classjoin.dto.JoinClassRequest
import com.sentencefit.application.classjoin.port.`in`.JoinClassUseCase
import com.sentencefit.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student/classes")
class StudentClassController(
    private val joinClassUseCase: JoinClassUseCase,
) {

    @PostMapping("/join")
    fun join(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @Valid @RequestBody request: JoinClassRequest,
    ) = ApiResponse.success(
        joinClassUseCase.execute(principal.userId, request),
        "클래스 참여 성공"
    )
}