package com.sentencefit.domain.teacherset.exception

class SetException(
    val errorCode: SetErrorCode,
) : RuntimeException(errorCode.message)