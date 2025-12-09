package com.sentencefit.application.teachersentence.service

import com.sentencefit.application.teachersentence.dto.SetSentenceResponse
import com.sentencefit.application.teachersentence.dto.UpdateSetSentenceRequest
import com.sentencefit.application.teachersentence.port.`in`.UpdateSetSentenceUseCase
import com.sentencefit.application.teachersentence.port.out.LoadSetSentencePort
import com.sentencefit.application.teachersentence.port.out.SaveSetSentencePort
import com.sentencefit.application.teacherset.port.out.LoadSetPort
import com.sentencefit.domain.teachersentence.exception.SetSentenceErrorCode
import com.sentencefit.domain.teachersentence.exception.SetSentenceException
import com.sentencefit.domain.teacherset.exception.SetErrorCode
import com.sentencefit.domain.teacherset.exception.SetException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateSetSentenceService(
    private val loadSetPort: LoadSetPort,
    private val loadSetSentencePort: LoadSetSentencePort,
    private val saveSetSentencePort: SaveSetSentencePort,
) : UpdateSetSentenceUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        sentenceId: Long,
        request: UpdateSetSentenceRequest,
    ): SetSentenceResponse {
        loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        val sentence = loadSetSentencePort.findBySetIdAndId(setId, sentenceId)
            ?: throw SetSentenceException(SetSentenceErrorCode.SET_SENTENCE_NOT_FOUND)

        val updated = sentence.update(
            orderNo = request.orderNo,
            displayCode = request.displayCode?.trim()?.takeIf { it.isNotBlank() },
            englishText = request.englishText.trim(),
            koreanText = request.koreanText?.trim()?.takeIf { it.isNotBlank() },
            grammarPoint = request.grammarPoint?.trim()?.takeIf { it.isNotBlank() },
        )

        return SetSentenceDtoMapper.toResponse(saveSetSentencePort.save(updated))
    }
}