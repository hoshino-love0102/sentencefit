package com.sentencefit.domain.teacherset.exception

enum class SetErrorCode(
    val message: String,
) {
    SET_NOT_FOUND("세트를 찾을 수 없습니다."),
    SET_FORBIDDEN("해당 세트에 접근할 수 없습니다."),
}