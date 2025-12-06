package com.sentencefit.application.auth.port.`in`

import com.sentencefit.application.auth.dto.RegisterRequest

interface RegisterUseCase {
    fun register(request: RegisterRequest)
}