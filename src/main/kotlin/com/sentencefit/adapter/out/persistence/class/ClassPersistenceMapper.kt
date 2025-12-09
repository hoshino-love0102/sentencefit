package com.sentencefit.adapter.out.persistence.`class`

import com.sentencefit.domain.teacherclass.model.TeacherClass
import java.time.LocalDateTime

object ClassPersistenceMapper {
    fun toDomain(entity: ClassJpaEntity): TeacherClass =
        TeacherClass(
            id = entity.id,
            teacherId = entity.teacherId,
            name = entity.name,
            description = entity.description,
            status = entity.status,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )

    fun toEntity(domain: TeacherClass): ClassJpaEntity =
        ClassJpaEntity(
            id = domain.id,
            teacherId = domain.teacherId,
            name = domain.name,
            description = domain.description,
            status = domain.status,
            createdAt = domain.createdAt ?: LocalDateTime.now(),
            updatedAt = domain.updatedAt ?: LocalDateTime.now(),
            deletedAt = domain.deletedAt,
        )
}