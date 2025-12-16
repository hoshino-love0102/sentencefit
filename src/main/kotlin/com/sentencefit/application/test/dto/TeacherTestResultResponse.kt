package com.sentencefit.application.test.dto

data class TeacherTestResultResponse(
    val testId: Long,
    val studentId: Long,
    val setId: Long,
    val status: String,
    val mode: String,
    val score: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val totalCount: Int,
    val retestCount: Int,
    val currentQuestionNo: Int,
    val questions: List<TeacherTestQuestionResultResponse>,
)