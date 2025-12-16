package com.sentencefit.adapter.out.persistence.test

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TeacherTestAccessJpaRepository : JpaRepository<StudentTestJpaEntity, Long> {

    @Query(
        value = """
            select exists(
                select 1
                from student_test st
                join sentence_set s on s.id = st.set_id
                join class c on c.id = s.class_id
                where st.id = :testId
                  and c.teacher_id = :teacherId
            )
        """,
        nativeQuery = true
    )
    fun existsAccessibleTest(
        @Param("teacherId") teacherId: Long,
        @Param("testId") testId: Long,
    ): Boolean
}