package com.sentencefit.application.classjoin.service

import com.sentencefit.application.classjoin.dto.JoinCodeResponse
import com.sentencefit.application.classjoin.dto.JoinedClassResponse
import com.sentencefit.domain.classjoin.model.ClassJoinCode
import com.sentencefit.domain.teacherclass.model.TeacherClass

object ClassJoinDtoMapper {
    fun toJoinCodeResponse(domain: ClassJoinCode): JoinCodeResponse =
        JoinCodeResponse(joinCode = domain.code)

    fun toJoinedClassResponse(domain: TeacherClass): JoinedClassResponse =
        JoinedClassResponse(
            classId = domain.id!!,
            className = domain.name,
        )
}