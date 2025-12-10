package com.sentencefit.domain.teacherset.exception

import com.sentencefit.common.exception.BusinessException

class SetException(
    errorCode: SetErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)