package com.sentencefit.application.teacherclass.port.out

import com.sentencefit.domain.teacherclass.model.TeacherClass

interface SaveClassPort {
    fun save(teacherClass: TeacherClass): TeacherClass
}