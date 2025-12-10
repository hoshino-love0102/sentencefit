package com.sentencefit.domain.teacherclass.exception

import com.sentencefit.common.exception.BusinessException

class ClassException(
    errorCode: ClassErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)