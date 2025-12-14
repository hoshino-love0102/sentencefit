package com.sentencefit.application.test.dto

import jakarta.validation.constraints.NotBlank

data class StartStudentTestRequest(
    @field:NotBlank(message = "mode는 필수입니다.")
    val mode: String,
)