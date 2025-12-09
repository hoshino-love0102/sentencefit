package com.sentencefit.application.teacherset.port.`in`

import com.sentencefit.application.teacherset.dto.SetResponse
import com.sentencefit.application.teacherset.dto.UpdateSetRequest

interface UpdateSetUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        request: UpdateSetRequest,
    ): SetResponse
}