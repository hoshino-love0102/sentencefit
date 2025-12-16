package com.sentencefit.application.test.port.out

interface LoadTeacherTestAccessPort {
    fun canAccessTest(
        teacherId: Long,
        testId: Long,
    ): Boolean
}