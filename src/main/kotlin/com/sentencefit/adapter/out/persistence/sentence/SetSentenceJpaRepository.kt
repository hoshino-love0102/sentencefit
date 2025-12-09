package com.sentencefit.adapter.out.persistence.sentence

import com.sentencefit.domain.teachersentence.model.SetSentenceStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SetSentenceJpaRepository : JpaRepository<SetSentenceJpaEntity, Long> {
    fun findByIdAndSetIdAndStatus(
        id: Long,
        setId: Long,
        status: SetSentenceStatus,
    ): SetSentenceJpaEntity?

    fun findAllBySetIdAndStatusOrderByOrderNoAsc(
        setId: Long,
        status: SetSentenceStatus,
    ): List<SetSentenceJpaEntity>

    fun findAllBySetIdAndStatus(
        setId: Long,
        status: SetSentenceStatus,
        pageable: Pageable,
    ): Page<SetSentenceJpaEntity>

    fun findAllBySetIdAndEnglishTextContainingIgnoreCaseAndStatus(
        setId: Long,
        keyword: String,
        status: SetSentenceStatus,
        pageable: Pageable,
    ): Page<SetSentenceJpaEntity>
}