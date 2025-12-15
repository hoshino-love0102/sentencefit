package com.sentencefit.adapter.out.persistence.test

import org.springframework.data.jpa.repository.JpaRepository

interface StudentTestQuestionJpaRepository : JpaRepository<StudentTestQuestionJpaEntity, Long> {
    fun findByTestIdAndQuestionNo(testId: Long, questionNo: Int): StudentTestQuestionJpaEntity?
    fun findAllByTestIdOrderByQuestionNoAsc(testId: Long): List<StudentTestQuestionJpaEntity>
}