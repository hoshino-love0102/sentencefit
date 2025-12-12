package com.sentencefit.application.classjoin.port.`in`

interface RemoveClassMemberUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        studentId: Long,
    )
}