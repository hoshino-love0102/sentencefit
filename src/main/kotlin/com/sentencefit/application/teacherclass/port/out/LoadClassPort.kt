package com.sentencefit.application.teacherclass.port.out

import com.sentencefit.domain.teacherclass.model.TeacherClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LoadClassPort {
    fun findByTeacherIdAndId(
        teacherId: Long,
        classId: Long,
    ): TeacherClass?

    fun findAllByTeacherId(
        teacherId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<TeacherClass>
}