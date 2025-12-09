package com.sentencefit.application.teacherclass.service

import com.sentencefit.application.teacherclass.dto.ClassResponse
import com.sentencefit.domain.teacherclass.model.TeacherClass

object ClassDtoMapper {
    fun toResponse(domain: TeacherClass): ClassResponse =
        ClassResponse(
            id = domain.id!!,
            teacherId = domain.teacherId,
            name = domain.name,
            description = domain.description,
            status = domain.status.name,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )
}