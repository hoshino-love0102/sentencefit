package com.sentencefit.adapter.out.persistence.test

import com.sentencefit.adapter.out.persistence.sentence.SetSentenceJpaRepository
import com.sentencefit.adapter.out.persistence.set.SetJpaRepository
import com.sentencefit.application.test.port.out.LoadSetForStudentTestPort
import com.sentencefit.application.test.port.out.StudentTestSentenceSource
import com.sentencefit.application.test.port.out.StudentTestSetSource
import com.sentencefit.domain.teachersentence.model.SetSentenceStatus
import com.sentencefit.domain.teacherset.model.SetStatus
import org.springframework.stereotype.Component

@Component
class StudentTestSetQueryAdapter(
    private val setJpaRepository: SetJpaRepository,
    private val setSentenceJpaRepository: SetSentenceJpaRepository,
) : LoadSetForStudentTestPort {

    override fun loadSet(setId: Long): StudentTestSetSource? {
        val entity = setJpaRepository.findById(setId).orElse(null) ?: return null

        if (entity.status != SetStatus.ACTIVE) {
            return null
        }

        return StudentTestSetSource(
            setId = entity.id!!,
            classId = entity.classId,
            teacherId = entity.teacherId,
            title = entity.title,
            isPublished = entity.isPublished,
        )
    }

    override fun loadSentences(setId: Long): List<StudentTestSentenceSource> {
        return setSentenceJpaRepository
            .findAllBySetIdAndStatusOrderByOrderNoAsc(
                setId = setId,
                status = SetSentenceStatus.ACTIVE,
            )
            .map { entity ->
                StudentTestSentenceSource(
                    sentenceId = entity.id!!,
                    orderNo = entity.orderNo,
                    englishText = entity.englishText,
                    koreanText = entity.koreanText,
                    grammarPoint = entity.grammarPoint,
                )
            }
    }
}