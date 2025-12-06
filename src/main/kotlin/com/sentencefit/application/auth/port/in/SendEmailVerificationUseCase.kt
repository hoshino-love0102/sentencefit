package com.sentencefit.application.auth.port.`in`

interface SendEmailVerificationUseCase {
    fun sendCode(email: String)
}