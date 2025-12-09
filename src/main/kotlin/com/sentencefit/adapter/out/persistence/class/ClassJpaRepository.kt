package com.sentencefit.adapter.out.persistence.`class`

import com.sentencefit.domain.teacherclass.model.ClassStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ClassJpaRepository : JpaRepository<ClassJpaEntity, Long> {
    fun findByIdAndTeacherIdAndStatus(
        id: Long,
        teacherId: Long,
        status: ClassStatus,
    ): ClassJpaEntity?

    fun findAllByTeacherIdAndStatus(
        teacherId: Long,
        status: ClassStatus,
        pageable: Pageable,
    ): Page<ClassJpaEntity>

    fun findAllByTeacherIdAndNameContainingIgnoreCaseAndStatus(
        teacherId: Long,
        keyword: String,
        status: ClassStatus,
        pageable: Pageable,
    ): Page<ClassJpaEntity>
}