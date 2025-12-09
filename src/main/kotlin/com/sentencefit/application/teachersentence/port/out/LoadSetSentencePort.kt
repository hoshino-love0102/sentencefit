package com.sentencefit.application.teachersentence.port.out

import com.sentencefit.domain.teachersentence.model.SetSentence
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LoadSetSentencePort {
    fun findBySetIdAndId(
        setId: Long,
        sentenceId: Long,
    ): SetSentence?

    fun findAllBySetId(
        setId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<SetSentence>

    fun findAllActiveBySetId(setId: Long): List<SetSentence>
}