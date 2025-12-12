package com.sentencefit.application.classjoin.port.out

import com.sentencefit.application.classjoin.dto.ClassMemberResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QueryClassMemberPort {
    fun findMembers(
        classId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassMemberResponse>
}