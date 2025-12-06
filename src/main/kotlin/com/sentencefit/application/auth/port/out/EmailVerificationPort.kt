package com.sentencefit.application.auth.port.out

import com.sentencefit.domain.auth.model.EmailVerification

interface EmailVerificationPort {
    fun save(emailVerification: EmailVerification, ttlSeconds: Long)
    fun find(email: String): EmailVerification?
    fun markVerified(email: String, ttlSeconds: Long)
    fun isVerified(email: String): Boolean
    fun delete(email: String)
}