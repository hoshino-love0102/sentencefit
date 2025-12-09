package com.sentencefit.domain.teacherclass.exception

class ClassException(
    val errorCode: ClassErrorCode,
) : RuntimeException(errorCode.message)