package com.sentencefit.application.classjoin.port.out

import com.sentencefit.domain.teacherclass.model.TeacherClass

interface LoadJoinTargetClassPort {
    fun findByTeacherIdAndId(teacherId: Long, classId: Long): TeacherClass?
    fun findById(classId: Long): TeacherClass?
}