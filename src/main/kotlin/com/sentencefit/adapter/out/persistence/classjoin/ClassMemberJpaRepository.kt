package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.domain.classjoin.model.ClassMemberStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ClassMemberJpaRepository : JpaRepository<ClassMemberJpaEntity, Long> {
    fun existsByClassIdAndStudentIdAndStatus(
        classId: Long,
        studentId: Long,
        status: ClassMemberStatus,
    ): Boolean
}