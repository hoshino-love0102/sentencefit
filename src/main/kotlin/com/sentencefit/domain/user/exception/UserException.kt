package com.sentencefit.domain.user.exception

class UserException(
    val errorCode: UserErrorCode,
    override val message: String = errorCode.message
) : RuntimeException(message)