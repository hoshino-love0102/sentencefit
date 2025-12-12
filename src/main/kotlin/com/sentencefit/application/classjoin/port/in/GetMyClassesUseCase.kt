package com.sentencefit.application.classjoin.port.`in`

import com.sentencefit.application.classjoin.dto.StudentClassSummaryResponse

interface GetMyClassesUseCase {
    fun execute(studentId: Long): List<StudentClassSummaryResponse>
}