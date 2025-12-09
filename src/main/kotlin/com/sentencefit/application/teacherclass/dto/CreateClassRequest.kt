package com.sentencefit.application.teacherclass.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateClassRequest(
    @field:NotBlank(message = "클래스명은 필수입니다.")
    @field:Size(max = 100, message = "클래스명은 100자 이하여야 합니다.")
    val name: String,

    @field:Size(max = 500, message = "설명은 500자 이하여야 합니다.")
    val description: String? = null,
)