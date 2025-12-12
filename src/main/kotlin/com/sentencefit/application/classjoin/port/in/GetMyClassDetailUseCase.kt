package com.sentencefit.application.classjoin.port.`in`

import com.sentencefit.application.classjoin.dto.StudentClassDetailResponse

interface GetMyClassDetailUseCase {
    fun execute(studentId: Long, classId: Long): StudentClassDetailResponse
}