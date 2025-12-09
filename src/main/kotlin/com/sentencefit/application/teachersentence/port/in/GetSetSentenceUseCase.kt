package com.sentencefit.application.teachersentence.port.`in`

import com.sentencefit.application.teachersentence.dto.SetSentenceResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetSetSentenceUseCase {
    fun get(
        teacherId: Long,
        classId: Long,
        setId: Long,
        sentenceId: Long,
    ): SetSentenceResponse

    fun getList(
        teacherId: Long,
        classId: Long,
        setId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<SetSentenceResponse>
}