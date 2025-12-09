package com.sentencefit.application.teacherclass.dto

import java.time.LocalDateTime

data class ClassResponse(
    val id: Long,
    val teacherId: Long,
    val name: String,
    val description: String?,
    val status: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
)