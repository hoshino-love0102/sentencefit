package com.sentencefit.adapter.out.persistence.sentence

import com.sentencefit.domain.teachersentence.model.SetSentence
import java.time.LocalDateTime

object SetSentencePersistenceMapper {
    fun toDomain(entity: SetSentenceJpaEntity): SetSentence =
        SetSentence(
            id = entity.id,
            setId = entity.setId,
            orderNo = entity.orderNo,
            displayCode = entity.displayCode,
            englishText = entity.englishText,
            koreanText = entity.koreanText,
            grammarPoint = entity.grammarPoint,
            status = entity.status,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )

    fun toEntity(domain: SetSentence): SetSentenceJpaEntity =
        SetSentenceJpaEntity(
            id = domain.id,
            setId = domain.setId,
            orderNo = domain.orderNo,
            displayCode = domain.displayCode,
            englishText = domain.englishText,
            koreanText = domain.koreanText,
            grammarPoint = domain.grammarPoint,
            status = domain.status,
            createdAt = domain.createdAt ?: LocalDateTime.now(),
            updatedAt = domain.updatedAt ?: LocalDateTime.now(),
            deletedAt = domain.deletedAt,
        )
}