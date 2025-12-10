package com.sentencefit.domain.auth.exception

import com.sentencefit.common.exception.BusinessException

class AuthException(
    errorCode: AuthErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)