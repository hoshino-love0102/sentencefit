package com.sentencefit.application.auth.port.`in`

interface VerifyEmailUseCase {
    fun verify(email: String, code: String)
}