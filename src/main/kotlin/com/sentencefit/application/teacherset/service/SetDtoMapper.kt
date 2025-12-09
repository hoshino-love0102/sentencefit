package com.sentencefit.application.teacherset.service

import com.sentencefit.application.teacherset.dto.SetResponse
import com.sentencefit.domain.teacherset.model.StudySet

object SetDtoMapper {
    fun toResponse(domain: StudySet): SetResponse =
        SetResponse(
            id = requireNotNull(domain.id) { "StudySet.id is null after save" },
            classId = domain.classId,
            teacherId = domain.teacherId,
            title = domain.title,
            description = domain.description,
            isPublished = domain.isPublished,
            status = domain.status.name,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )
}