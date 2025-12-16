package com.sentencefit.application.test.dto

data class TeacherTestQuestionResultResponse(
    val questionId: Long,
    val questionNo: Int,
    val koreanText: String?,
    val grammarPoint: String?,
    val isCorrect: Boolean,
    val correctAnswer: String,
    val studentAnswer: String?,
    val errorType: String?,
    val explanation: String?,
    val canRetest: Boolean,
    val steps: List<TeacherTestStepResultResponse>,
)