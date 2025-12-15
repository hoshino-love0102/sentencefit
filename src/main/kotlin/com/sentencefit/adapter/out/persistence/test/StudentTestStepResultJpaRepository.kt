package com.sentencefit.adapter.out.persistence.test

import org.springframework.data.jpa.repository.JpaRepository

interface StudentTestStepResultJpaRepository : JpaRepository<StudentTestStepResultJpaEntity, Long> {
    fun findByQuestionIdAndStepNo(questionId: Long, stepNo: Int): StudentTestStepResultJpaEntity?
    fun findAllByQuestionIdOrderByStepNoAsc(questionId: Long): List<StudentTestStepResultJpaEntity>
    fun findAllByTestIdOrderByQuestionIdAscStepNoAsc(testId: Long): List<StudentTestStepResultJpaEntity>
}