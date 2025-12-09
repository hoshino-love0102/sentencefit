package com.sentencefit.application.teachersentence.dto

import java.time.LocalDateTime

data class SetSentenceResponse(
    val id: Long,
    val setId: Long,
    val orderNo: Int,
    val displayCode: String?,
    val englishText: String,
    val koreanText: String?,
    val grammarPoint: String?,
    val status: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
)