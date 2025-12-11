package com.sentencefit.application.classjoin.port.out

interface LoadClassMemberPort {
    fun existsActiveMember(classId: Long, studentId: Long): Boolean
}