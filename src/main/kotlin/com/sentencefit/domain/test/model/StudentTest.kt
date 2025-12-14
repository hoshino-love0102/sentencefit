package com.sentencefit.domain.test.model

import java.time.LocalDateTime

data class StudentTest(
    val id: Long? = null,
    val studentId: Long,
    val setId: Long,
    val mode: TestMode,
    val status: TestStatus = TestStatus.IN_PROGRESS,
    val totalQuestions: Int,
    val currentQuestionNo: Int = 1,
    val score: Int = 0,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val retestCount: Int = 0,
    val createdAt: LocalDateTime? = null,
    val completedAt: LocalDateTime? = null,
) {
    fun complete(
        score: Int,
        correctCount: Int,
        wrongCount: Int,
        retestCount: Int,
    ): StudentTest = copy(
        status = TestStatus.COMPLETED,
        score = score,
        correctCount = correctCount,
        wrongCount = wrongCount,
        retestCount = retestCount,
        completedAt = LocalDateTime.now(),
        currentQuestionNo = totalQuestions,
    )

    fun moveCurrentQuestion(questionNo: Int): StudentTest =
        copy(currentQuestionNo = questionNo)
}