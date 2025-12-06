package com.sentencefit.application.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class EmailVerifyRequest(
    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    @field:Size(min = 6, max = 6, message = "인증코드는 6자리여야 합니다.")
    val code: String
)