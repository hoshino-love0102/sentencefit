package com.sentencefit.domain.test.exception

import com.sentencefit.common.exception.BusinessException

class StudentTestException(
    errorCode: StudentTestErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)