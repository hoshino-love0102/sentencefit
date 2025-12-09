package com.sentencefit.domain.teacherclass.model

import java.time.LocalDateTime

data class TeacherClass(
    val id: Long? = null,
    val teacherId: Long,
    val name: String,
    val description: String? = null,
    val status: ClassStatus = ClassStatus.ACTIVE,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    fun update(
        name: String,
        description: String?,
    ): TeacherClass = copy(
        name = name,
        description = description,
        updatedAt = LocalDateTime.now(),
    )

    fun delete(): TeacherClass = copy(
        status = ClassStatus.DELETED,
        deletedAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
}