package com.sentencefit.application.classjoin.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class JoinClassRequest(
    @field:NotBlank(message = "참여 코드는 필수입니다.")
    @field:Size(min = 4, max = 20, message = "참여 코드 길이가 올바르지 않습니다.")
    val joinCode: String,
)