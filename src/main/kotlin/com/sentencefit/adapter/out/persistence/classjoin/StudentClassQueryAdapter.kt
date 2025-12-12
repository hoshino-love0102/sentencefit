package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.application.classjoin.dto.StudentClassDetailResponse
import com.sentencefit.application.classjoin.dto.StudentClassSummaryResponse
import com.sentencefit.application.classjoin.port.out.QueryStudentClassPort
import org.springframework.stereotype.Component

@Component
class StudentClassQueryAdapter(
    private val studentClassQueryRepository: StudentClassQueryRepository,
) : QueryStudentClassPort {

    override fun findMyClasses(studentId: Long): List<StudentClassSummaryResponse> =
        studentClassQueryRepository.findMyClasses(studentId)

    override fun findMyClassDetail(studentId: Long, classId: Long): StudentClassDetailResponse? =
        studentClassQueryRepository.findMyClassDetail(studentId, classId)
}