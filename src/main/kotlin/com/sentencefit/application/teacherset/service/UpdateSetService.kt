package com.sentencefit.application.teacherset.service

import com.sentencefit.application.teacherset.dto.SetResponse
import com.sentencefit.application.teacherset.dto.UpdateSetRequest
import com.sentencefit.application.teacherset.port.`in`.UpdateSetUseCase
import com.sentencefit.application.teacherset.port.out.LoadSetPort
import com.sentencefit.application.teacherset.port.out.SaveSetPort
import com.sentencefit.domain.teacherset.exception.SetErrorCode
import com.sentencefit.domain.teacherset.exception.SetException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateSetService(
    private val loadSetPort: LoadSetPort,
    private val saveSetPort: SaveSetPort,
) : UpdateSetUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        request: UpdateSetRequest,
    ): SetResponse {
        val studySet = loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        if (studySet.status.name == "DELETED") {
            throw SetException(SetErrorCode.SET_ALREADY_DELETED)
        }

        val updated = studySet.update(
            title = request.title,
            description = request.description,
            isPublished = request.isPublished,
        )

        return SetDtoMapper.toResponse(saveSetPort.save(updated))
    }
}