package com.sentencefit.domain.teachersentence.exception

import com.sentencefit.common.exception.BusinessException

class SetSentenceException(
    errorCode: SetSentenceErrorCode,
    message: String = errorCode.message,
) : BusinessException(errorCode, message)