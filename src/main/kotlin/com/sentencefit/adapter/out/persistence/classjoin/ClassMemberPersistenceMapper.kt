package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.domain.classjoin.model.ClassMember
import java.time.LocalDateTime

object ClassMemberPersistenceMapper {
    fun toDomain(entity: ClassMemberJpaEntity): ClassMember =
        ClassMember(
            id = entity.id,
            classId = entity.classId,
            studentId = entity.studentId,
            status = entity.status,
            joinedAt = entity.joinedAt,
        )

    fun toEntity(domain: ClassMember): ClassMemberJpaEntity =
        ClassMemberJpaEntity(
            id = domain.id,
            classId = domain.classId,
            studentId = domain.studentId,
            status = domain.status,
            joinedAt = domain.joinedAt ?: LocalDateTime.now(),
        )
}