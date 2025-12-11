package com.sentencefit.domain.classjoin.model

import java.time.LocalDateTime

data class ClassJoinCode(
    val id: Long? = null,
    val classId: Long,
    val code: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    fun regenerate(newCode: String): ClassJoinCode = copy(
        code = newCode,
        updatedAt = LocalDateTime.now(),
    )
}