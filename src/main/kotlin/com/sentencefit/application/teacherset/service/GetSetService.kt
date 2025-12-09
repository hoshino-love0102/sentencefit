package com.sentencefit.application.teacherset.service

import com.sentencefit.application.teacherset.dto.SetResponse
import com.sentencefit.application.teacherset.port.`in`.GetSetUseCase
import com.sentencefit.application.teacherset.port.out.LoadSetPort
import com.sentencefit.domain.teacherset.exception.SetErrorCode
import com.sentencefit.domain.teacherset.exception.SetException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetSetService(
    private val loadSetPort: LoadSetPort,
) : GetSetUseCase {

    @Transactional(readOnly = true)
    override fun get(
        teacherId: Long,
        classId: Long,
        setId: Long,
    ): SetResponse {
        val studySet = loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        return SetDtoMapper.toResponse(studySet)
    }

    @Transactional(readOnly = true)
    override fun getList(
        teacherId: Long,
        classId: Long,
        keyword: String?,
        isPublished: Boolean?,
        pageable: Pageable,
    ): Page<SetResponse> {
        return loadSetPort.findAllByTeacherIdAndClassId(
            teacherId = teacherId,
            classId = classId,
            keyword = keyword?.trim(),
            isPublished = isPublished,
            pageable = pageable,
        ).map(SetDtoMapper::toResponse)
    }
}