package com.sentencefit.domain.auth.exception

class AuthException(
    val errorCode: AuthErrorCode,
    override val message: String = errorCode.message
) : RuntimeException(message)