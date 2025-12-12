package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.application.classjoin.dto.ClassMemberResponse
import com.sentencefit.application.classjoin.port.out.QueryClassMemberPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ClassMemberQueryAdapter(
    private val classMemberQueryRepository: ClassMemberQueryRepository,
) : QueryClassMemberPort {

    override fun findMembers(
        classId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassMemberResponse> =
        classMemberQueryRepository.findMembers(
            classId = classId,
            keyword = keyword,
            pageable = pageable,
        )
}