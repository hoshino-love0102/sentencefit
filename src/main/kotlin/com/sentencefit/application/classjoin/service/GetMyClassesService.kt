package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.dto.StudentClassSummaryResponse
import com.sentencefit.application.classjoin.port.`in`.GetMyClassesUseCase
import com.sentencefit.application.classjoin.port.out.QueryStudentClassPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyClassesService(
    private val queryStudentClassPort: QueryStudentClassPort,
) : GetMyClassesUseCase {

    @Transactional(readOnly = true)
    override fun execute(studentId: Long): List<StudentClassSummaryResponse> =
        queryStudentClassPort.findMyClasses(studentId)
}