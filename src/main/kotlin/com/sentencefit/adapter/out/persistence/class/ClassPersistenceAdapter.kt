package com.sentencefit.adapter.out.persistence.`class`

import com.sentencefit.application.teacherclass.port.out.LoadClassPort
import com.sentencefit.application.teacherclass.port.out.SaveClassPort
import com.sentencefit.domain.teacherclass.model.ClassStatus
import com.sentencefit.domain.teacherclass.model.TeacherClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ClassPersistenceAdapter(
    private val classJpaRepository: ClassJpaRepository,
) : SaveClassPort, LoadClassPort {

    override fun save(teacherClass: TeacherClass): TeacherClass {
        val saved = classJpaRepository.save(ClassPersistenceMapper.toEntity(teacherClass))
        return ClassPersistenceMapper.toDomain(saved)
    }

    override fun findByTeacherIdAndId(
        teacherId: Long,
        classId: Long,
    ): TeacherClass? {
        return classJpaRepository.findByIdAndTeacherIdAndStatus(
            id = classId,
            teacherId = teacherId,
            status = ClassStatus.ACTIVE,
        )?.let(ClassPersistenceMapper::toDomain)
    }

    override fun findAllByTeacherId(
        teacherId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<TeacherClass> {
        val result = if (keyword.isNullOrBlank()) {
            classJpaRepository.findAllByTeacherIdAndStatus(
                teacherId = teacherId,
                status = ClassStatus.ACTIVE,
                pageable = pageable,
            )
        } else {
            classJpaRepository.findAllByTeacherIdAndNameContainingIgnoreCaseAndStatus(
                teacherId = teacherId,
                keyword = keyword,
                status = ClassStatus.ACTIVE,
                pageable = pageable,
            )
        }

        return result.map(ClassPersistenceMapper::toDomain)
    }
}