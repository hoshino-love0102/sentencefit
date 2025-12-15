package com.sentencefit.adapter.out.ai.test

data class AiGradingItemRequest(
    val questionId: Long,
    val stepNo: Int,
    val stepType: String,
    val answerType: String,
    val englishText: String,
    val koreanText: String?,
    val grammarPoint: String?,
    val correctAnswer: String,
    val studentAnswer: Any,
)

data class AiGradingBatchRequest(
    val items: List<AiGradingItemRequest>,
)

data class AiGradingItemResponse(
    val questionId: Long,
    val stepNo: Int,
    val isCorrect: Boolean,
    val correctAnswer: String,
    val explanation: String?,
    val errorType: String?,
    val usedAi: Boolean,
    val canRetest: Boolean,
)

data class AiGradingBatchResponse(
    val results: List<AiGradingItemResponse>,
)