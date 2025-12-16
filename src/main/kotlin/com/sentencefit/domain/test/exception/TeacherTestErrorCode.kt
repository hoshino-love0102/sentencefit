package com.sentencefit.domain.test.exception

import com.sentencefit.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class TeacherTestErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "테스트를 찾을 수 없습니다."),
    TEST_NOT_ACCESSIBLE(HttpStatus.FORBIDDEN, "해당 테스트 결과에 접근할 수 없습니다."),
}