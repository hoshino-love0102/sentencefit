package com.sentencefit.application.teacherclass.port.`in`

import com.sentencefit.application.teacherclass.dto.ClassResponse
import com.sentencefit.application.teacherclass.dto.UpdateClassRequest

interface UpdateClassUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        request: UpdateClassRequest,
    ): ClassResponse
}