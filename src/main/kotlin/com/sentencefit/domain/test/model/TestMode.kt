package com.sentencefit.domain.test.model

enum class TestMode {
    NORMAL,
    RETEST;

    companion object {
        fun from(value: String): TestMode =
            entries.firstOrNull { it.name == value.uppercase() }
                ?: throw IllegalArgumentException("지원하지 않는 TestMode 입니다: $value")
    }
}