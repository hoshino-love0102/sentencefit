package com.sentencefit.domain.test.model

data class StudentTestQuestion(
    val id: Long? = null,
    val testId: Long,
    val sentenceId: Long,
    val questionNo: Int,
    val englishText: String,
    val koreanText: String?,
    val grammarPoint: String?,
    val tokens: List<String>,
)