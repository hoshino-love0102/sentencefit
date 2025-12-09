package com.sentencefit.application.teachersentence.port.`in`

import com.sentencefit.application.teachersentence.dto.UpdateSentenceOrderRequest

interface UpdateSentenceOrderUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        request: UpdateSentenceOrderRequest,
    )
}