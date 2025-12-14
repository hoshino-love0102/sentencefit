package com.sentencefit.application.test.dto

data class StudentTestQuestionResponse(
    val questionId: Long,
    val questionNo: Int,
    val stepNo: Int,
    val stepType: String,
    val answerType: String,
    val koreanText: String?,
    val grammarPoint: String?,
    val tokens: List<String>?,
    val isLastStep: Boolean,
    val submitted: Boolean,
)