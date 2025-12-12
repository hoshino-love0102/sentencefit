package com.sentencefit.application.classjoin.port.out

import com.sentencefit.application.classjoin.dto.StudentClassDetailResponse
import com.sentencefit.application.classjoin.dto.StudentClassSummaryResponse

interface QueryStudentClassPort {
    fun findMyClasses(studentId: Long): List<StudentClassSummaryResponse>
    fun findMyClassDetail(studentId: Long, classId: Long): StudentClassDetailResponse?
}