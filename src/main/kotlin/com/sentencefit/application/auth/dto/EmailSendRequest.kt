package com.sentencefit.application.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailSendRequest(
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    @field:NotBlank(message = "이메일은 필수입니다.")
    val email: String
)