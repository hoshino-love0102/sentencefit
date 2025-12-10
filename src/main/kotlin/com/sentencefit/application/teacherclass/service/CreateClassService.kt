package com.sentencefit.application.teacherclass.service

import com.sentencefit.application.teacherclass.dto.ClassResponse
import com.sentencefit.application.teacherclass.dto.CreateClassRequest
import com.sentencefit.application.teacherclass.port.`in`.CreateClassUseCase
import com.sentencefit.application.teacherclass.port.out.SaveClassPort
import com.sentencefit.domain.teacherclass.model.TeacherClass
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateClassService(
    private val saveClassPort: SaveClassPort,
) : CreateClassUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        request: CreateClassRequest,
    ): ClassResponse {
        val teacherClass = TeacherClass(
            teacherId = teacherId,
            name = request.name,
            description = request.description,
        )

        return ClassDtoMapper.toResponse(saveClassPort.save(teacherClass))
    }
}