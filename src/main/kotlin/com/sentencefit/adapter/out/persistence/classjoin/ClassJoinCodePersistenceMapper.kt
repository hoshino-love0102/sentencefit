package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.domain.classjoin.model.ClassJoinCode
import java.time.LocalDateTime

object ClassJoinCodePersistenceMapper {
    fun toDomain(entity: ClassJoinCodeJpaEntity): ClassJoinCode =
        ClassJoinCode(
            id = entity.id,
            classId = entity.classId,
            code = entity.code,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )

    fun toEntity(domain: ClassJoinCode): ClassJoinCodeJpaEntity =
        ClassJoinCodeJpaEntity(
            id = domain.id,
            classId = domain.classId,
            code = domain.code,
            createdAt = domain.createdAt ?: LocalDateTime.now(),
            updatedAt = domain.updatedAt ?: LocalDateTime.now(),
        )
}