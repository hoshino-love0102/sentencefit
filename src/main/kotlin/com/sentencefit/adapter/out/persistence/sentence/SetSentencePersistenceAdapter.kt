package com.sentencefit.adapter.out.persistence.sentence

import com.sentencefit.application.teachersentence.port.out.LoadSetSentencePort
import com.sentencefit.application.teachersentence.port.out.SaveSetSentencePort
import com.sentencefit.domain.teachersentence.model.SetSentence
import com.sentencefit.domain.teachersentence.model.SetSentenceStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class SetSentencePersistenceAdapter(
    private val setSentenceJpaRepository: SetSentenceJpaRepository,
) : SaveSetSentencePort, LoadSetSentencePort {

    override fun save(sentence: SetSentence): SetSentence {
        val saved = setSentenceJpaRepository.save(SetSentencePersistenceMapper.toEntity(sentence))
        return SetSentencePersistenceMapper.toDomain(saved)
    }

    override fun saveAll(sentences: List<SetSentence>): List<SetSentence> {
        return setSentenceJpaRepository.saveAll(
            sentences.map(SetSentencePersistenceMapper::toEntity)
        ).map(SetSentencePersistenceMapper::toDomain)
    }

    override fun findBySetIdAndId(
        setId: Long,
        sentenceId: Long,
    ): SetSentence? {
        return setSentenceJpaRepository.findByIdAndSetIdAndStatus(
            id = sentenceId,
            setId = setId,
            status = SetSentenceStatus.ACTIVE,
        )?.let(SetSentencePersistenceMapper::toDomain)
    }

    override fun findAllBySetId(
        setId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<SetSentence> {
        val result = if (keyword.isNullOrBlank()) {
            setSentenceJpaRepository.findAllBySetIdAndStatus(
                setId = setId,
                status = SetSentenceStatus.ACTIVE,
                pageable = pageable,
            )
        } else {
            setSentenceJpaRepository.findAllBySetIdAndEnglishTextContainingIgnoreCaseAndStatus(
                setId = setId,
                keyword = keyword,
                status = SetSentenceStatus.ACTIVE,
                pageable = pageable,
            )
        }

        return result.map(SetSentencePersistenceMapper::toDomain)
    }

    override fun findAllActiveBySetId(setId: Long): List<SetSentence> {
        return setSentenceJpaRepository.findAllBySetIdAndStatusOrderByOrderNoAsc(
            setId = setId,
            status = SetSentenceStatus.ACTIVE,
        ).map(SetSentencePersistenceMapper::toDomain)
    }
}