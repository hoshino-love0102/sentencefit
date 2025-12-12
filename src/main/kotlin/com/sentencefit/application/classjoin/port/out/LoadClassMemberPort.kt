package com.sentencefit.application.classjoin.port.out

import com.sentencefit.domain.classjoin.model.ClassMember

interface LoadClassMemberPort {
    fun existsActiveMember(classId: Long, studentId: Long): Boolean
    fun findActiveMember(classId: Long, studentId: Long): ClassMember?
}