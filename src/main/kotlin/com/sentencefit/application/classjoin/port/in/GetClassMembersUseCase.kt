package com.sentencefit.application.classjoin.port.`in`

import com.sentencefit.application.classjoin.dto.ClassMemberResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetClassMembersUseCase {
    fun execute(
        teacherId: Long,
        classId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassMemberResponse>
}