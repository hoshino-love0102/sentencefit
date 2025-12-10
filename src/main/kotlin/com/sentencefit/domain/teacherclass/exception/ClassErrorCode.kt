package com.sentencefit.domain.teacherclass.exception

import com.sentencefit.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class ClassErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "클래스를 찾을 수 없습니다."),
    CLASS_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 클래스입니다."),
    CLASS_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 클래스에 접근할 수 없습니다."),
    CLASS_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "클래스명은 필수입니다."),
    CLASS_NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "클래스명은 100자 이하이어야 합니다."),
    CLASS_DESCRIPTION_TOO_LONG(HttpStatus.BAD_REQUEST, "설명은 500자 이하이어야 합니다."),
}