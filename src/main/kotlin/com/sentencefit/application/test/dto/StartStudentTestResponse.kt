package com.sentencefit.application.test.dto

data class StartStudentTestResponse(
    val testId: Long,
    val totalQuestions: Int,
    val currentQuestionNo: Int,
    val status: String,
)