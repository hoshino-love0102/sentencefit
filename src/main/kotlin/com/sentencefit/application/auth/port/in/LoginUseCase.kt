package com.sentencefit.application.auth.port.`in`

import com.sentencefit.application.auth.dto.LoginRequest
import com.sentencefit.application.auth.dto.TokenResponse

interface LoginUseCase {
    fun login(request: LoginRequest): TokenResponse
}