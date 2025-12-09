package com.sentencefit.application.teachersentence.dto

import jakarta.validation.constraints.Min

data class UpdateSentenceOrderItem(
    val sentenceId: Long,

    @field:Min(value = 1, message = "순서는 1 이상이어야 합니다.")
    val orderNo: Int,
)