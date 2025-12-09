package com.sentencefit.application.teacherclass.service

import com.sentencefit.application.teacherclass.dto.ClassResponse
import com.sentencefit.application.teacherclass.port.`in`.GetClassUseCase
import com.sentencefit.application.teacherclass.port.out.LoadClassPort
import com.sentencefit.domain.teacherclass.exception.ClassErrorCode
import com.sentencefit.domain.teacherclass.exception.ClassException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetClassService(
    private val loadClassPort: LoadClassPort,
) : GetClassUseCase {

    @Transactional(readOnly = true)
    override fun get(
        teacherId: Long,
        classId: Long,
    ): ClassResponse {
        val teacherClass = loadClassPort.findByTeacherIdAndId(teacherId, classId)
            ?: throw ClassException(ClassErrorCode.CLASS_NOT_FOUND)

        return ClassDtoMapper.toResponse(teacherClass)
    }

    @Transactional(readOnly = true)
    override fun getList(
        teacherId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassResponse> {
        return loadClassPort.findAllByTeacherId(
            teacherId = teacherId,
            keyword = keyword?.trim(),
            pageable = pageable,
        ).map(ClassDtoMapper::toResponse)
    }
}