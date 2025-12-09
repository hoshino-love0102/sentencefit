package com.sentencefit.application.teachersentence.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateSetSentenceRequest(
    @field:Min(value = 1, message = "순서는 1 이상이어야 합니다.")
    val orderNo: Int,

    @field:Size(max = 20, message = "표시 코드는 20자 이하여야 합니다.")
    val displayCode: String? = null,

    @field:NotBlank(message = "영문 문장은 필수입니다.")
    @field:Size(max = 2000, message = "영문 문장은 2000자 이하여야 합니다.")
    val englishText: String,

    @field:Size(max = 2000, message = "해석은 2000자 이하여야 합니다.")
    val koreanText: String? = null,

    @field:Size(max = 500, message = "문법 포인트는 500자 이하여야 합니다.")
    val grammarPoint: String? = null,
)