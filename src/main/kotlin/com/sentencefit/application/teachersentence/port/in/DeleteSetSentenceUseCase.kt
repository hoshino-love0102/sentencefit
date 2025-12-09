package com.sentencefit.application.teachersentence.port.`in`

interface DeleteSetSentenceUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        sentenceId: Long,
    )
}