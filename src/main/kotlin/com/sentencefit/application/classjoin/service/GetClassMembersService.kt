package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.dto.ClassMemberResponse
import com.sentencefit.application.classjoin.port.`in`.GetClassMembersUseCase
import com.sentencefit.application.classjoin.port.out.LoadJoinTargetClassPort
import com.sentencefit.application.classjoin.port.out.QueryClassMemberPort
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetClassMembersService(
    private val loadJoinTargetClassPort: LoadJoinTargetClassPort,
    private val queryClassMemberPort: QueryClassMemberPort,
) : GetClassMembersUseCase {

    @Transactional(readOnly = true)
    override fun execute(
        teacherId: Long,
        classId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassMemberResponse> {
        loadJoinTargetClassPort.findByTeacherIdAndId(teacherId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)

        return queryClassMemberPort.findMembers(classId, keyword, pageable)
    }
}