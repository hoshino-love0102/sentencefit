package com.sentencefit.application.teacherset.service

import com.sentencefit.application.teacherclass.port.out.LoadClassPort
import com.sentencefit.application.teacherset.dto.CreateSetRequest
import com.sentencefit.application.teacherset.dto.SetResponse
import com.sentencefit.application.teacherset.port.`in`.CreateSetUseCase
import com.sentencefit.application.teacherset.port.out.SaveSetPort
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import com.sentencefit.domain.teacherset.model.StudySet
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateSetService(
    private val loadClassPort: LoadClassPort,
    private val saveSetPort: SaveSetPort,
) : CreateSetUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        request: CreateSetRequest,
    ): SetResponse {
        val teacherClass = loadClassPort.findByTeacherIdAndId(teacherId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)

        if (teacherClass.status.name == "DELETED") {
            throw ClassException(ClassErrorCode.CLASS_ALREADY_DELETED)
        }

        val studySet = StudySet(
            classId = classId,
            teacherId = teacherId,
            title = request.title,
            description = request.description,
            isPublished = request.isPublished,
        )

        return SetDtoMapper.toResponse(saveSetPort.save(studySet))
    }
}