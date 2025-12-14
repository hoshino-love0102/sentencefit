package com.sentencefit.application.test.dto

data class StudentTestQuestionResultResponse(
    val questionId: Long,
    val questionNo: Int,
    val isCorrect: Boolean,
    val correctAnswer: String,
    val studentAnswer: String?,
    val errorType: String?,
    val explanation: String?,
    val canRetest: Boolean,
)