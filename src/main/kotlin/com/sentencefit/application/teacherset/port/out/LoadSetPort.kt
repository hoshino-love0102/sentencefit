package com.sentencefit.application.teacherset.port.out

import com.sentencefit.domain.teacherset.model.StudySet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LoadSetPort {
    fun findByTeacherIdAndClassIdAndId(
        teacherId: Long,
        classId: Long,
        setId: Long,
    ): StudySet?

    fun findAllByTeacherIdAndClassId(
        teacherId: Long,
        classId: Long,
        keyword: String?,
        isPublished: Boolean?,
        pageable: Pageable,
    ): Page<StudySet>
}