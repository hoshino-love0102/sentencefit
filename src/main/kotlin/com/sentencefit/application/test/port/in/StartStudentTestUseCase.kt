package com.sentencefit.application.test.port.`in`

import com.sentencefit.application.test.dto.StartStudentTestRequest
import com.sentencefit.application.test.dto.StartStudentTestResponse

interface StartStudentTestUseCase {
    fun start(studentId: Long, setId: Long, request: StartStudentTestRequest): StartStudentTestResponse
}