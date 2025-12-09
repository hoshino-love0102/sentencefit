package com.sentencefit.domain.teacherclass.exception

enum class ClassErrorCode(
    val message: String,
) {
    CLASS_NOT_FOUND("클래스를 찾을 수 없습니다."),
}