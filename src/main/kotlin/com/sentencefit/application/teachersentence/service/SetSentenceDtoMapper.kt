package com.sentencefit.application.teachersentence.service

import com.sentencefit.application.teachersentence.dto.SetSentenceResponse
import com.sentencefit.domain.teachersentence.model.SetSentence

object SetSentenceDtoMapper {
    fun toResponse(domain: SetSentence): SetSentenceResponse =
        SetSentenceResponse(
            id = domain.id!!,
            setId = domain.setId,
            orderNo = domain.orderNo,
            displayCode = domain.displayCode,
            englishText = domain.englishText,
            koreanText = domain.koreanText,
            grammarPoint = domain.grammarPoint,
            status = domain.status.name,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )
}