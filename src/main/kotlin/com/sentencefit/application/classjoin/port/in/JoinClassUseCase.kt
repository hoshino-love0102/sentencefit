package com.sentencefit.application.classjoin.port.`in`

import com.sentencefit.application.classjoin.dto.JoinClassRequest
import com.sentencefit.application.classjoin.dto.JoinedClassResponse

interface JoinClassUseCase {
    fun execute(studentId: Long, request: JoinClassRequest): JoinedClassResponse
}