package com.sentencefit.application.teachersentence.port.`in`

import com.sentencefit.application.teachersentence.dto.CreateSetSentenceRequest
import com.sentencefit.application.teachersentence.dto.SetSentenceResponse

interface CreateSetSentenceUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        request: CreateSetSentenceRequest,
    ): SetSentenceResponse
}