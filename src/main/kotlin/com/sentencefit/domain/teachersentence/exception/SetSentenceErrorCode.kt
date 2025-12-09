package com.sentencefit.domain.teachersentence.exception

enum class SetSentenceErrorCode(
    val message: String,
) {
    SET_SENTENCE_NOT_FOUND("문장을 찾을 수 없습니다."),
    INVALID_SENTENCE_ORDER("문장 순서 요청이 올바르지 않습니다."),
}