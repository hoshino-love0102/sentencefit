package com.sentencefit.application.teachersentence.port.`in`

import com.sentencefit.application.teachersentence.dto.SetSentenceResponse
import com.sentencefit.application.teachersentence.dto.UpdateSetSentenceRequest

interface UpdateSetSentenceUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        sentenceId: Long,
        request: UpdateSetSentenceRequest,
    ): SetSentenceResponse
}