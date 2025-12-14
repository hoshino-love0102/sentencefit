package com.sentencefit.domain.test.model

import java.time.LocalDateTime

data class StudentTestStepResult(
    val id: Long? = null,
    val testId: Long,
    val questionId: Long,
    val stepNo: Int,
    val stepType: StepType,
    val answerType: AnswerType,
    val submittedAnswerText: String? = null,
    val submittedAnswerTokens: List<String>? = null,
    val isCorrect: Boolean,
    val correctAnswer: String,
    val explanation: String?,
    val errorType: ErrorType?,
    val usedAi: Boolean,
    val canRetest: Boolean,
    val createdAt: LocalDateTime? = null,
)