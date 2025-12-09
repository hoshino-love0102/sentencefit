package com.sentencefit.application.teacherset.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateSetRequest(
    @field:NotBlank(message = "세트 제목은 필수입니다.")
    @field:Size(max = 150, message = "세트 제목은 150자 이하여야 합니다.")
    val title: String,

    @field:Size(max = 1000, message = "설명은 1000자 이하여야 합니다.")
    val description: String? = null,

    val isPublished: Boolean,
)