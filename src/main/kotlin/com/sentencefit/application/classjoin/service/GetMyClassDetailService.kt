package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.dto.StudentClassDetailResponse
import com.sentencefit.application.classjoin.port.`in`.GetMyClassDetailUseCase
import com.sentencefit.application.classjoin.port.out.QueryStudentClassPort
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyClassDetailService(
    private val queryStudentClassPort: QueryStudentClassPort,
) : GetMyClassDetailUseCase {

    @Transactional(readOnly = true)
    override fun execute(studentId: Long, classId: Long): StudentClassDetailResponse =
        queryStudentClassPort.findMyClassDetail(studentId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)
}