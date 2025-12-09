package com.sentencefit.application.teacherset.dto

import java.time.LocalDateTime

data class SetResponse(
    val id: Long,
    val classId: Long,
    val teacherId: Long,
    val title: String,
    val description: String?,
    val isPublished: Boolean,
    val status: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
)