package com.sentencefit.domain.classjoin.exception

import com.sentencefit.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class ClassJoinErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    JOIN_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "참여 코드를 찾을 수 없습니다."),
    INVALID_JOIN_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 참여 코드입니다."),
    ALREADY_JOINED_CLASS(HttpStatus.CONFLICT, "이미 참여한 클래스입니다."),
    ONLY_STUDENT_CAN_JOIN(HttpStatus.FORBIDDEN, "학생만 클래스에 참여할 수 있습니다."),
    JOIN_TARGET_CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "참여 대상 클래스를 찾을 수 없습니다."),
}