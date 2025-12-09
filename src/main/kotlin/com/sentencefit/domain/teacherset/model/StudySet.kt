package com.sentencefit.domain.teacherset.model

import java.time.LocalDateTime

data class StudySet(
    val id: Long? = null,
    val classId: Long,
    val teacherId: Long,
    val title: String,
    val description: String? = null,
    val isPublished: Boolean = false,
    val status: SetStatus = SetStatus.ACTIVE,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    fun update(title: String, description: String?, isPublished: Boolean): StudySet = copy(
        title = title,
        description = description,
        isPublished = isPublished,
        updatedAt = LocalDateTime.now(),
    )

    fun delete(): StudySet = copy(
        status = SetStatus.DELETED,
        deletedAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
}