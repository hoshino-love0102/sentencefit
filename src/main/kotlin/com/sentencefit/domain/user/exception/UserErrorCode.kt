package com.sentencefit.domain.user.exception

import org.springframework.http.HttpStatus

enum class UserErrorCode(
    val status: HttpStatus,
    val message: String
) {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.")
}