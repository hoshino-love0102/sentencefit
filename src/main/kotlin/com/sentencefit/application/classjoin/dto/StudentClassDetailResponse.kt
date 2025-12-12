package com.sentencefit.application.classjoin.dto

data class StudentClassDetailResponse(
    val classId: Long,
    val name: String,
    val sets: List<StudentClassSetResponse>,
)