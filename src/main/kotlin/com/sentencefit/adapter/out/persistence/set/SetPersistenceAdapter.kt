package com.sentencefit.adapter.out.persistence.set

import com.sentencefit.application.teacherset.port.out.LoadSetPort
import com.sentencefit.application.teacherset.port.out.SaveSetPort
import com.sentencefit.domain.teacherset.model.SetStatus
import com.sentencefit.domain.teacherset.model.StudySet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class SetPersistenceAdapter(
    private val setJpaRepository: SetJpaRepository,
) : SaveSetPort, LoadSetPort {

    override fun save(studySet: StudySet): StudySet {
        val saved = setJpaRepository.save(SetPersistenceMapper.toEntity(studySet))
        return SetPersistenceMapper.toDomain(saved)
    }

    override fun findByTeacherIdAndClassIdAndId(
        teacherId: Long,
        classId: Long,
        setId: Long,
    ): StudySet? {
        return setJpaRepository.findByIdAndTeacherIdAndClassIdAndStatus(
            id = setId,
            teacherId = teacherId,
            classId = classId,
            status = SetStatus.ACTIVE,
        )?.let(SetPersistenceMapper::toDomain)
    }

    override fun findAllByTeacherIdAndClassId(
        teacherId: Long,
        classId: Long,
        keyword: String?,
        isPublished: Boolean?,
        pageable: Pageable,
    ): Page<StudySet> {
        val result = when {
            keyword.isNullOrBlank() && isPublished == null ->
                setJpaRepository.findAllByTeacherIdAndClassIdAndStatus(
                    teacherId = teacherId,
                    classId = classId,
                    status = SetStatus.ACTIVE,
                    pageable = pageable,
                )

            !keyword.isNullOrBlank() && isPublished == null ->
                setJpaRepository.findAllByTeacherIdAndClassIdAndTitleContainingIgnoreCaseAndStatus(
                    teacherId = teacherId,
                    classId = classId,
                    keyword = keyword,
                    status = SetStatus.ACTIVE,
                    pageable = pageable,
                )

            keyword.isNullOrBlank() && isPublished != null ->
                setJpaRepository.findAllByTeacherIdAndClassIdAndIsPublishedAndStatus(
                    teacherId = teacherId,
                    classId = classId,
                    isPublished = isPublished,
                    status = SetStatus.ACTIVE,
                    pageable = pageable,
                )

            else ->
                setJpaRepository.findAllByTeacherIdAndClassIdAndTitleContainingIgnoreCaseAndIsPublishedAndStatus(
                    teacherId = teacherId,
                    classId = classId,
                    keyword = keyword!!,
                    isPublished = isPublished!!,
                    status = SetStatus.ACTIVE,
                    pageable = pageable,
                )
        }

        return result.map(SetPersistenceMapper::toDomain)
    }
}