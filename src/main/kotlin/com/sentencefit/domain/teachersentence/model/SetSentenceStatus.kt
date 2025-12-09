package com.sentencefit.domain.teachersentence.model

import java.time.LocalDateTime

data class SetSentence(
    val id: Long? = null,
    val setId: Long,
    val orderNo: Int,
    val displayCode: String? = null,
    val englishText: String,
    val koreanText: String? = null,
    val grammarPoint: String? = null,
    val status: SetSentenceStatus = SetSentenceStatus.ACTIVE,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    fun update(
        orderNo: Int,
        displayCode: String?,
        englishText: String,
        koreanText: String?,
        grammarPoint: String?,
    ): SetSentence = copy(
        orderNo = orderNo,
        displayCode = displayCode,
        englishText = englishText,
        koreanText = koreanText,
        grammarPoint = grammarPoint,
        updatedAt = LocalDateTime.now(),
    )

    fun updateOrder(orderNo: Int): SetSentence = copy(
        orderNo = orderNo,
        updatedAt = LocalDateTime.now(),
    )

    fun delete(): SetSentence = copy(
        status = SetSentenceStatus.DELETED,
        deletedAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
}