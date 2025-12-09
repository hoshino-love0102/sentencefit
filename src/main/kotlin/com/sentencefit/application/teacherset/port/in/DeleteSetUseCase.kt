package com.sentencefit.application.teacherset.port.`in`

interface DeleteSetUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
    )
}