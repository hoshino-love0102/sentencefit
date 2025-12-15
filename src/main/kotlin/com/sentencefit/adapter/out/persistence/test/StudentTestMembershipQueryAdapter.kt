package com.sentencefit.adapter.out.persistence.test

import com.sentencefit.adapter.out.persistence.classjoin.ClassMemberJpaRepository
import com.sentencefit.application.test.port.out.LoadStudentMembershipPort
import com.sentencefit.domain.classjoin.model.ClassMemberStatus
import org.springframework.stereotype.Component

@Component
class StudentTestMembershipQueryAdapter(
    private val classMemberJpaRepository: ClassMemberJpaRepository,
) : LoadStudentMembershipPort {

    override fun existsActiveMembership(classId: Long, studentId: Long): Boolean {
        return classMemberJpaRepository.existsByClassIdAndStudentIdAndStatus(
            classId = classId,
            studentId = studentId,
            status = ClassMemberStatus.ACTIVE,
        )
    }
}