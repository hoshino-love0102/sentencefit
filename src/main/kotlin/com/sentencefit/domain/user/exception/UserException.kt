package com.sentencefit.domain.user.exception

import com.sentencefit.common.exception.BusinessException

class UserException(
    errorCode: UserErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)