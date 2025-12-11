package com.sentencefit.application.classjoin.port.`in`

import com.sentencefit.application.classjoin.dto.JoinCodeResponse

interface RegenerateJoinCodeUseCase {
    fun execute(teacherId: Long, classId: Long): JoinCodeResponse
}