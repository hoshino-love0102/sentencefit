package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.dto.JoinCodeResponse
import com.sentencefit.application.classjoin.port.`in`.RegenerateJoinCodeUseCase
import com.sentencefit.application.classjoin.port.out.LoadJoinCodePort
import com.sentencefit.application.classjoin.port.out.LoadJoinTargetClassPort
import com.sentencefit.application.classjoin.port.out.SaveJoinCodePort
import com.sentencefit.domain.classjoin.model.ClassJoinCode
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegenerateJoinCodeService(
    private val loadJoinTargetClassPort: LoadJoinTargetClassPort,
    private val loadJoinCodePort: LoadJoinCodePort,
    private val saveJoinCodePort: SaveJoinCodePort,
) : RegenerateJoinCodeUseCase {

    @Transactional
    override fun execute(teacherId: Long, classId: Long): JoinCodeResponse {
        loadJoinTargetClassPort.findByTeacherIdAndId(teacherId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)

        val existing = loadJoinCodePort.findByClassId(classId)
        val newCode = generateUniqueCode()

        val saved = if (existing == null) {
            saveJoinCodePort.save(
                ClassJoinCode(
                    classId = classId,
                    code = newCode,
                )
            )
        } else {
            saveJoinCodePort.save(existing.regenerate(newCode))
        }

        return ClassJoinDtoMapper.toJoinCodeResponse(saved)
    }

    private fun generateUniqueCode(): String {
        repeat(20) {
            val candidate = ClassJoinCodeGenerator.generate()
            if (loadJoinCodePort.findByCode(candidate) == null) {
                return candidate
            }
        }
        throw IllegalStateException("참여 코드 재생성에 실패했습니다.")
    }
}