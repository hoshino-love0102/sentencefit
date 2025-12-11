package com.sentencefit.domain.classjoin.exception

import com.sentencefit.common.exception.BusinessException

class ClassJoinException(
    errorCode: ClassJoinErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)