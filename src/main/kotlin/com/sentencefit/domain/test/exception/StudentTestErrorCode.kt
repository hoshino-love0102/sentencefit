package com.sentencefit.domain.test.exception

import com.sentencefit.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class StudentTestErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "테스트를 찾을 수 없습니다."),
    TEST_NOT_ACCESSIBLE(HttpStatus.FORBIDDEN, "해당 테스트에 접근할 수 없습니다."),
    TEST_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 완료된 테스트입니다."),
    TEST_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "문제를 찾을 수 없습니다."),
    INVALID_STEP_NO(HttpStatus.BAD_REQUEST, "유효하지 않은 단계 번호입니다."),
    STEP_ALREADY_SUBMITTED(HttpStatus.CONFLICT, "이미 제출한 단계입니다."),
    SET_NOT_FOUND(HttpStatus.NOT_FOUND, "세트를 찾을 수 없습니다."),
    SET_NOT_ACCESSIBLE(HttpStatus.FORBIDDEN, "해당 세트에 접근할 수 없습니다."),
    TEST_SENTENCE_EMPTY(HttpStatus.BAD_REQUEST, "테스트를 시작할 문장이 없습니다."),
    AI_GRADING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI 채점 중 오류가 발생했습니다."),
}