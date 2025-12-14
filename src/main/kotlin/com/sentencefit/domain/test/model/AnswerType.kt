package com.sentencefit.domain.test.model

enum class AnswerType {
    TOKEN_ARRAY,
    TEXT;

    companion object {
        fun from(value: String): AnswerType =
            entries.firstOrNull { it.name == value.uppercase() }
                ?: throw IllegalArgumentException("지원하지 않는 AnswerType 입니다: $value")
    }
}