package com.sentencefit.application.teachersentence.service

import com.sentencefit.application.teachersentence.dto.CreateSetSentenceRequest
import com.sentencefit.application.teachersentence.dto.SetSentenceResponse
import com.sentencefit.application.teachersentence.port.`in`.CreateSetSentenceUseCase
import com.sentencefit.application.teachersentence.port.out.SaveSetSentencePort
import com.sentencefit.application.teacherset.port.out.LoadSetPort
import com.sentencefit.domain.teachersentence.model.SetSentence
import com.sentencefit.domain.teacherset.exception.SetErrorCode
import com.sentencefit.domain.teacherset.exception.SetException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateSetSentenceService(
    private val loadSetPort: LoadSetPort,
    private val saveSetSentencePort: SaveSetSentencePort,
) : CreateSetSentenceUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        request: CreateSetSentenceRequest,
    ): SetSentenceResponse {
        loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        val sentence = SetSentence(
            setId = setId,
            orderNo = request.orderNo,
            displayCode = request.displayCode?.trim()?.takeIf { it.isNotBlank() },
            englishText = request.englishText.trim(),
            koreanText = request.koreanText?.trim()?.takeIf { it.isNotBlank() },
            grammarPoint = request.grammarPoint?.trim()?.takeIf { it.isNotBlank() },
        )

        return SetSentenceDtoMapper.toResponse(saveSetSentencePort.save(sentence))
    }
}