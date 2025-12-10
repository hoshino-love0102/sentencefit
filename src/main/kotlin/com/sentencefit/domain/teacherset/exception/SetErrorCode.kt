package com.sentencefit.domain.teacherset.exception

import com.sentencefit.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class SetErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    SET_NOT_FOUND(HttpStatus.NOT_FOUND, "세트를 찾을 수 없습니다."),
    SET_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 세트입니다."),
    SET_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 세트에 접근할 수 없습니다."),
    SET_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "세트 제목은 필수입니다."),
    SET_TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "세트 제목은 150자 이하이어야 합니다."),
    SET_DESCRIPTION_TOO_LONG(HttpStatus.BAD_REQUEST, "설명은 1000자 이하이어야 합니다."),
    SET_PUBLISH_STATE_INVALID(HttpStatus.BAD_REQUEST, "세트 공개 상태 값이 올바르지 않습니다."),
}