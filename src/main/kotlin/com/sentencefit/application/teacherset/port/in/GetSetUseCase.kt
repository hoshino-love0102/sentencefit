package com.sentencefit.application.teacherset.port.`in`

import com.sentencefit.application.teacherset.dto.SetResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetSetUseCase {
    fun get(
        teacherId: Long,
        classId: Long,
        setId: Long,
    ): SetResponse

    fun getList(
        teacherId: Long,
        classId: Long,
        keyword: String?,
        isPublished: Boolean?,
        pageable: Pageable,
    ): Page<SetResponse>
}