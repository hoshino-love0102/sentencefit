package com.sentencefit.application.test.dto

data class TeacherTestStepResultResponse(
    val stepNo: Int,
    val stepType: String,
    val answerType: String,
    val studentAnswer: String?,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val explanation: String?,
    val errorType: String?,
    val usedAi: Boolean,
    val canRetest: Boolean,
)