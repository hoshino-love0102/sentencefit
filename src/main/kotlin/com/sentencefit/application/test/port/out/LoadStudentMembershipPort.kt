package com.sentencefit.application.test.port.out

interface LoadStudentMembershipPort {
    fun existsActiveMembership(classId: Long, studentId: Long): Boolean
}