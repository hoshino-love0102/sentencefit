package com.sentencefit.domain.teachersentence.exception

class SetSentenceException(
    val errorCode: SetSentenceErrorCode,
) : RuntimeException(errorCode.message)