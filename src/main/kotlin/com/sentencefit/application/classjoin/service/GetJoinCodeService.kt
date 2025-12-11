package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.dto.JoinCodeResponse
import com.sentencefit.application.classjoin.port.`in`.GetJoinCodeUseCase
import com.sentencefit.application.classjoin.port.out.LoadJoinCodePort
import com.sentencefit.application.classjoin.port.out.LoadJoinTargetClassPort
import com.sentencefit.domain.classjoin.exception.ClassJoinErrorCode
import com.sentencefit.domain.classjoin.exception.ClassJoinException
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetJoinCodeService(
    private val loadJoinTargetClassPort: LoadJoinTargetClassPort,
    private val loadJoinCodePort: LoadJoinCodePort,
) : GetJoinCodeUseCase {

    @Transactional(readOnly = true)
    override fun execute(teacherId: Long, classId: Long): JoinCodeResponse {
        loadJoinTargetClassPort.findByTeacherIdAndId(teacherId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)

        val joinCode = loadJoinCodePort.findByClassId(classId)
            ?: throw ClassJoinException(ClassJoinErrorCode.JOIN_CODE_NOT_FOUND)

        return ClassJoinDtoMapper.toJoinCodeResponse(joinCode)
    }
}