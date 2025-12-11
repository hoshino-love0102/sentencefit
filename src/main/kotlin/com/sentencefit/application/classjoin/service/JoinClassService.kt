package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.dto.JoinClassRequest
import com.sentencefit.application.classjoin.dto.JoinedClassResponse
import com.sentencefit.application.classjoin.port.`in`.JoinClassUseCase
import com.sentencefit.application.classjoin.port.out.LoadClassMemberPort
import com.sentencefit.application.classjoin.port.out.LoadJoinCodePort
import com.sentencefit.application.classjoin.port.out.LoadJoinTargetClassPort
import com.sentencefit.application.classjoin.port.out.LoadUserPort
import com.sentencefit.application.classjoin.port.out.SaveClassMemberPort
import com.sentencefit.domain.classjoin.exception.ClassJoinErrorCode
import com.sentencefit.domain.classjoin.exception.ClassJoinException
import com.sentencefit.domain.classjoin.model.ClassMember
import com.sentencefit.domain.user.exception.UserErrorCode
import com.sentencefit.domain.user.exception.UserException
import com.sentencefit.domain.user.model.UserRole
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JoinClassService(
    private val loadUserPort: LoadUserPort,
    private val loadJoinCodePort: LoadJoinCodePort,
    private val loadJoinTargetClassPort: LoadJoinTargetClassPort,
    private val loadClassMemberPort: LoadClassMemberPort,
    private val saveClassMemberPort: SaveClassMemberPort,
) : JoinClassUseCase {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun execute(studentId: Long, request: JoinClassRequest): JoinedClassResponse {
        log.info("JoinClass start. studentId={}, rawJoinCode={}", studentId, request.joinCode)

        val user = loadUserPort.findById(studentId)
            ?: throw UserException(UserErrorCode.USER_NOT_FOUND)

        log.info("Loaded user. id={}, email={}, role={}", user.id, user.email, user.role)

        if (user.role != UserRole.STUDENT) {
            throw ClassJoinException(ClassJoinErrorCode.ONLY_STUDENT_CAN_JOIN)
        }

        val normalizedCode = request.joinCode.trim().uppercase()
        log.info("Normalized joinCode={}", normalizedCode)

        val joinCode = loadJoinCodePort.findByCode(normalizedCode)
            ?: throw ClassJoinException(ClassJoinErrorCode.INVALID_JOIN_CODE)

        log.info("Loaded joinCode. classId={}, code={}", joinCode.classId, joinCode.code)

        val teacherClass = loadJoinTargetClassPort.findById(joinCode.classId)
            ?: throw ClassJoinException(ClassJoinErrorCode.JOIN_TARGET_CLASS_NOT_FOUND)

        log.info("Loaded class. id={}, name={}", teacherClass.id, teacherClass.name)

        val alreadyJoined = loadClassMemberPort.existsActiveMember(teacherClass.id!!, studentId)
        log.info("alreadyJoined={}", alreadyJoined)

        if (alreadyJoined) {
            throw ClassJoinException(ClassJoinErrorCode.ALREADY_JOINED_CLASS)
        }

        val savedMember = saveClassMemberPort.save(
            ClassMember(
                classId = teacherClass.id,
                studentId = studentId,
            )
        )

        log.info(
            "JoinClass saved member. id={}, classId={}, studentId={}",
            savedMember.id, savedMember.classId, savedMember.studentId
        )

        return ClassJoinDtoMapper.toJoinedClassResponse(teacherClass)
    }
}