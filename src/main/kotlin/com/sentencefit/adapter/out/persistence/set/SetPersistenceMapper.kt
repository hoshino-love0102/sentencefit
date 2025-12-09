package com.sentencefit.adapter.out.persistence.set

import com.sentencefit.domain.teacherset.model.StudySet
import java.time.LocalDateTime

object SetPersistenceMapper {
    fun toDomain(entity: SetJpaEntity): StudySet =
        StudySet(
            id = entity.id,
            classId = entity.classId,
            teacherId = entity.teacherId,
            title = entity.title,
            description = entity.description,
            isPublished = entity.isPublished,
            status = entity.status,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )

    fun toEntity(domain: StudySet): SetJpaEntity =
        SetJpaEntity(
            id = domain.id,
            classId = domain.classId,
            teacherId = domain.teacherId,
            title = domain.title,
            description = domain.description,
            isPublished = domain.isPublished,
            status = domain.status,
            createdAt = domain.createdAt ?: LocalDateTime.now(),
            updatedAt = domain.updatedAt ?: LocalDateTime.now(),
            deletedAt = domain.deletedAt,
        )
}