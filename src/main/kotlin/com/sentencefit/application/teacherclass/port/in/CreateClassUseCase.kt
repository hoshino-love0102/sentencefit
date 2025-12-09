package com.sentencefit.application.teacherclass.port.`in`

import com.sentencefit.application.teacherclass.dto.ClassResponse
import com.sentencefit.application.teacherclass.dto.CreateClassRequest

interface CreateClassUseCase {
    fun execute(
        teacherId: Long,
        request: CreateClassRequest,
    ): ClassResponse
}