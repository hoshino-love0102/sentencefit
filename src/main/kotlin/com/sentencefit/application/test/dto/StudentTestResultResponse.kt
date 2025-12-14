package com.sentencefit.application.test.dto

data class StudentTestResultResponse(
    val testId: Long,
    val setId: Long,
    val status: String,
    val score: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val totalCount: Int,
    val retestCount: Int,
    val questions: List<StudentTestQuestionResultResponse>,
)