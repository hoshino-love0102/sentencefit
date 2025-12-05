package com.sentencefit.domain.auth.exception

import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    val status: HttpStatus,
    val message: String
) {
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "이메일 인증이 필요합니다."),
    INVALID_EMAIL_CODE(HttpStatus.BAD_REQUEST, "인증코드가 올바르지 않습니다."),
    EMAIL_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "인증코드가 만료되었거나 존재하지 않습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.")
}