package com.sentencefit.application.teacherclass.service

import com.sentencefit.application.teacherclass.dto.ClassResponse
import com.sentencefit.application.teacherclass.dto.UpdateClassRequest
import com.sentencefit.application.teacherclass.port.`in`.UpdateClassUseCase
import com.sentencefit.application.teacherclass.port.out.LoadClassPort
import com.sentencefit.application.teacherclass.port.out.SaveClassPort
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateClassService(
    private val loadClassPort: LoadClassPort,
    private val saveClassPort: SaveClassPort,
) : UpdateClassUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        request: UpdateClassRequest,
    ): ClassResponse {
        val teacherClass = loadClassPort.findByTeacherIdAndId(teacherId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)

        if (teacherClass.status.name == "DELETED") {
            throw ClassException(ClassErrorCode.CLASS_ALREADY_DELETED)
        }

        val updated = teacherClass.update(
            name = request.name,
            description = request.description,
        )

        return ClassDtoMapper.toResponse(saveClassPort.save(updated))
    }
}