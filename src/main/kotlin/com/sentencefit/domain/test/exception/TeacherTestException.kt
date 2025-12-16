package com.sentencefit.domain.test.exception

import com.sentencefit.common.exception.BusinessException

class TeacherTestException(
    errorCode: TeacherTestErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)