package com.sentencefit.adapter.`in`.web

import com.sentencefit.application.auth.dto.EmailSendRequest
import com.sentencefit.application.auth.dto.EmailVerifyRequest
import com.sentencefit.application.auth.dto.LoginRequest
import com.sentencefit.application.auth.dto.RegisterRequest
import com.sentencefit.application.auth.dto.TokenResponse
import com.sentencefit.application.auth.port.`in`.LoginUseCase
import com.sentencefit.application.auth.port.`in`.RegisterUseCase
import com.sentencefit.application.auth.port.`in`.SendEmailVerificationUseCase
import com.sentencefit.application.auth.port.`in`.VerifyEmailUseCase
import com.sentencefit.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) {

    @PostMapping("/email/send")
    fun sendEmailCode(
        @Valid @RequestBody request: EmailSendRequest
    ): ResponseEntity<ApiResponse<Void>> {
        sendEmailVerificationUseCase.sendCode(request.email)
        return ResponseEntity.ok(ApiResponse.success(null, "인증코드 발송 성공"))
    }

    @PostMapping("/email/verify")
    fun verifyEmailCode(
        @Valid @RequestBody request: EmailVerifyRequest
    ): ResponseEntity<ApiResponse<Void>> {
        verifyEmailUseCase.verify(request.email, request.code)
        return ResponseEntity.ok(ApiResponse.success(null, "이메일 인증 성공"))
    }

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody request: RegisterRequest
    ): ResponseEntity<ApiResponse<Void>> {
        registerUseCase.register(request)
        return ResponseEntity.ok(ApiResponse.success(null, "회원가입 성공"))
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<ApiResponse<TokenResponse>> {
        return ResponseEntity.ok(ApiResponse.success(loginUseCase.login(request), "로그인 성공"))
    }
}