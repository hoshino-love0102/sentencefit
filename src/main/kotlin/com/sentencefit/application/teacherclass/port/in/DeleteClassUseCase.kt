package com.sentencefit.application.teacherclass.port.`in`

interface DeleteClassUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
    )
}