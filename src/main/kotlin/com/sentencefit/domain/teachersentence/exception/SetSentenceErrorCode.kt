package com.sentencefit.domain.teachersentence.exception

import com.sentencefit.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class SetSentenceErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    SET_SENTENCE_NOT_FOUND(HttpStatus.NOT_FOUND, "문장을 찾을 수 없습니다."),
    SET_SENTENCE_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 문장입니다."),
    SET_SENTENCE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 문장에 접근할 수 없습니다."),

    INVALID_SENTENCE_ORDER(HttpStatus.BAD_REQUEST, "문장 순서 요청이 올바르지 않습니다."),
    DUPLICATE_SENTENCE_ORDER(HttpStatus.BAD_REQUEST, "문장 순서가 중복되었습니다."),
    EMPTY_SENTENCE_ORDER_ITEMS(HttpStatus.BAD_REQUEST, "문장 순서 변경 대상이 비어 있습니다."),
    SENTENCE_ID_REQUIRED(HttpStatus.BAD_REQUEST, "문장 ID는 필수입니다."),
    SENTENCE_ORDER_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, "문장 순서는 1 이상이어야 합니다."),

    ENGLISH_TEXT_REQUIRED(HttpStatus.BAD_REQUEST, "영문 문장은 필수입니다."),
    ENGLISH_TEXT_TOO_LONG(HttpStatus.BAD_REQUEST, "영문 문장은 2000자 이하여야 합니다."),
    KOREAN_TEXT_TOO_LONG(HttpStatus.BAD_REQUEST, "해석은 2000자 이하여야 합니다."),
    GRAMMAR_POINT_TOO_LONG(HttpStatus.BAD_REQUEST, "문법 포인트는 500자 이하여야 합니다."),
    DISPLAY_CODE_TOO_LONG(HttpStatus.BAD_REQUEST, "표시 코드는 20자 이하여야 합니다."),

    SET_NOT_FOUND(HttpStatus.NOT_FOUND, "문장이 속한 세트를 찾을 수 없습니다."),
    CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "문장이 속한 클래스를 찾을 수 없습니다."),
}