package com.sentencefit.adapter.out.persistence.set

import com.sentencefit.domain.teacherset.model.SetStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SetJpaRepository : JpaRepository<SetJpaEntity, Long> {
    fun findByIdAndTeacherIdAndClassIdAndStatus(
        id: Long,
        teacherId: Long,
        classId: Long,
        status: SetStatus,
    ): SetJpaEntity?

    fun findAllByTeacherIdAndClassIdAndStatus(
        teacherId: Long,
        classId: Long,
        status: SetStatus,
        pageable: Pageable,
    ): Page<SetJpaEntity>

    fun findAllByTeacherIdAndClassIdAndTitleContainingIgnoreCaseAndStatus(
        teacherId: Long,
        classId: Long,
        keyword: String,
        status: SetStatus,
        pageable: Pageable,
    ): Page<SetJpaEntity>

    fun findAllByTeacherIdAndClassIdAndIsPublishedAndStatus(
        teacherId: Long,
        classId: Long,
        isPublished: Boolean,
        status: SetStatus,
        pageable: Pageable,
    ): Page<SetJpaEntity>

    fun findAllByTeacherIdAndClassIdAndTitleContainingIgnoreCaseAndIsPublishedAndStatus(
        teacherId: Long,
        classId: Long,
        keyword: String,
        isPublished: Boolean,
        status: SetStatus,
        pageable: Pageable,
    ): Page<SetJpaEntity>
}