package com.sentencefit.adapter.`in`.web

import com.sentencefit.adapter.`in`.security.CustomUserPrincipal
import com.sentencefit.application.user.dto.MyInfoResponse
import com.sentencefit.application.user.port.`in`.GetMyInfoUseCase
import com.sentencefit.common.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val getMyInfoUseCase: GetMyInfoUseCase
) {

    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal principal: CustomUserPrincipal
    ): ResponseEntity<ApiResponse<MyInfoResponse>> {
        val user = getMyInfoUseCase.getById(principal.userId)
        return ResponseEntity.ok(ApiResponse.success(MyInfoResponse.from(user), "내 정보 조회 성공"))
    }
}