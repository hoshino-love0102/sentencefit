package com.sentencefit.application.teachersentence.dto

import jakarta.validation.Valid

data class UpdateSentenceOrderRequest(
    @field:Valid
    val items: List<UpdateSentenceOrderItem>,
)