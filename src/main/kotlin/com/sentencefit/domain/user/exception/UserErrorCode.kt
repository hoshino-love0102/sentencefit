package com.sentencefit.domain.user.exception

import com.sentencefit.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 사용자 정보에 접근할 수 없습니다."),
    INVALID_USER_ROLE(HttpStatus.BAD_REQUEST, "사용자 역할이 올바르지 않습니다."),
}