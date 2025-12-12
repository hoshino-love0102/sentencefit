package com.sentencefit.application.classjoin.dto

import java.time.LocalDateTime

data class ClassMemberResponse(
    val studentId: Long,
    val name: String,
    val email: String,
    val joinedAt: LocalDateTime,
    val status: String,
)