package com.sentencefit.application.teacherclass.port.`in`

import com.sentencefit.application.teacherclass.dto.ClassResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetClassUseCase {
    fun get(
        teacherId: Long,
        classId: Long,
    ): ClassResponse

    fun getList(
        teacherId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassResponse>
}