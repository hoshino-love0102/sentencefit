package com.sentencefit.adapter.out.persistence.classjoin

import org.springframework.data.jpa.repository.JpaRepository

interface ClassJoinCodeJpaRepository : JpaRepository<ClassJoinCodeJpaEntity, Long> {
    fun findByClassId(classId: Long): ClassJoinCodeJpaEntity?
    fun findByCode(code: String): ClassJoinCodeJpaEntity?
}