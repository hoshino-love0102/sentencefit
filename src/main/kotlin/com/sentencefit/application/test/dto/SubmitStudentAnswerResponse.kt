package com.sentencefit.application.test.dto

data class SubmitStudentAnswerResponse(
    val isCorrect: Boolean,
    val correctAnswer: String,
    val explanation: String?,
    val errorType: String?,
    val nextStepNo: Int?,
    val nextQuestionNo: Int?,
    val usedAi: Boolean,
    val canRetest: Boolean,
    val testStatus: String,
    val isTestCompleted: Boolean,
)