package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.port.`in`.RemoveClassMemberUseCase
import com.sentencefit.application.classjoin.port.out.LoadClassMemberPort
import com.sentencefit.application.classjoin.port.out.LoadJoinTargetClassPort
import com.sentencefit.application.classjoin.port.out.SaveClassMemberPort
import com.sentencefit.domain.classjoin.exception.ClassJoinErrorCode
import com.sentencefit.domain.classjoin.exception.ClassJoinException
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RemoveClassMemberService(
    private val loadJoinTargetClassPort: LoadJoinTargetClassPort,
    private val loadClassMemberPort: LoadClassMemberPort,
    private val saveClassMemberPort: SaveClassMemberPort,
) : RemoveClassMemberUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        studentId: Long,
    ) {
        loadJoinTargetClassPort.findByTeacherIdAndId(teacherId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)

        val member = loadClassMemberPort.findActiveMember(classId, studentId)
            ?: throw ClassJoinException(ClassJoinErrorCode.CLASS_MEMBER_NOT_FOUND)

        saveClassMemberPort.save(member.remove())
    }
}