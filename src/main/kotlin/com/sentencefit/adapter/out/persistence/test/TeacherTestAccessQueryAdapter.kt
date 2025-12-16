package com.sentencefit.adapter.out.persistence.test

import com.sentencefit.application.test.port.out.LoadTeacherTestAccessPort
import org.springframework.stereotype.Component

@Component
class TeacherTestAccessQueryAdapter(
    private val teacherTestAccessJpaRepository: TeacherTestAccessJpaRepository,
) : LoadTeacherTestAccessPort {

    override fun canAccessTest(
        teacherId: Long,
        testId: Long,
    ): Boolean {
        return teacherTestAccessJpaRepository.existsAccessibleTest(
            teacherId = teacherId,
            testId = testId,
        )
    }
}