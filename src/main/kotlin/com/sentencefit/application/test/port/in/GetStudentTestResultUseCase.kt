package com.sentencefit.application.test.port.`in`

import com.sentencefit.application.test.dto.StudentTestResultResponse

interface GetStudentTestResultUseCase {
    fun getResult(studentId: Long, testId: Long): StudentTestResultResponse
}