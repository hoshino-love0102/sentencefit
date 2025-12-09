package com.sentencefit.application.teacherset.port.`in`

import com.sentencefit.application.teacherset.dto.CreateSetRequest
import com.sentencefit.application.teacherset.dto.SetResponse

interface CreateSetUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        request: CreateSetRequest,
    ): SetResponse
}