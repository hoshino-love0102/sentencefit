package com.sentencefit.application.test.port.`in`

import com.sentencefit.application.test.dto.SubmitStudentAnswerRequest
import com.sentencefit.application.test.dto.SubmitStudentAnswerResponse

interface SubmitStudentAnswerUseCase {
    fun submit(studentId: Long, testId: Long, request: SubmitStudentAnswerRequest): SubmitStudentAnswerResponse
}