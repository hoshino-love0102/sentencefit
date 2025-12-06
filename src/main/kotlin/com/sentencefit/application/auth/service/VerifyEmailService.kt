package com.sentencefit.application.auth.service

import com.sentencefit.application.auth.port.`in`.VerifyEmailUseCase
import com.sentencefit.application.auth.port.out.EmailVerificationPort
import com.sentencefit.domain.auth.exception.AuthErrorCode
import com.sentencefit.domain.auth.exception.AuthException
import org.springframework.stereotype.Service

@Service
class VerifyEmailService(
    private val emailVerificationPort: EmailVerificationPort
) : VerifyEmailUseCase {

    companion object {
        private const val VERIFIED_TTL_SECONDS = 1800L
    }

    override fun verify(email: String, code: String) {
        val saved = emailVerificationPort.find(email)
            ?: throw AuthException(AuthErrorCode.EMAIL_CODE_EXPIRED)

        if (saved.code != code) {
            throw AuthException(AuthErrorCode.INVALID_EMAIL_CODE)
        }

        emailVerificationPort.markVerified(email, VERIFIED_TTL_SECONDS)
    }
}