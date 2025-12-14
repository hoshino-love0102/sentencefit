package com.sentencefit.application.test.port.out

import com.sentencefit.domain.test.model.AnswerType
import com.sentencefit.domain.test.model.ErrorType
import com.sentencefit.domain.test.model.StepType

interface AiTestGradingPort {
    fun gradeAll(commands: List<AiTestGradingCommand>): List<AiTestGradingResult>
}

data class AiTestGradingCommand(
    val questionId: Long,
    val questionNo: Int,
    val stepNo: Int,
    val stepType: StepType,
    val answerType: AnswerType,
    val englishText: String,
    val correctAnswer: String,
    val submittedAnswerText: String? = null,
    val submittedAnswerTokens: List<String>? = null,
    val koreanText: String? = null,
    val grammarPoint: String? = null,
)

data class AiTestGradingResult(
    val questionId: Long,
    val stepNo: Int,
    val isCorrect: Boolean,
    val correctAnswer: String,
    val explanation: String?,
    val errorType: ErrorType?,
    val usedAi: Boolean,
    val canRetest: Boolean,
)