package com.sentencefit.application.test.port.`in`

import com.sentencefit.application.test.dto.StudentTestQuestionResponse

interface GetStudentTestQuestionUseCase {
    fun getQuestion(studentId: Long, testId: Long, no: Int): StudentTestQuestionResponse
}