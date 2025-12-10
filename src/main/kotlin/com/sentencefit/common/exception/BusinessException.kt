package com.sentencefit.common.exception

abstract class BusinessException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message,
) : RuntimeException(message)