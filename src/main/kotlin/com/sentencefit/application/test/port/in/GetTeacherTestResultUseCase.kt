package com.sentencefit.application.test.port.`in`

import com.sentencefit.application.test.dto.TeacherTestResultResponse

interface GetTeacherTestResultUseCase {
    fun getResult(
        teacherId: Long,
        testId: Long,
    ): TeacherTestResultResponse
}