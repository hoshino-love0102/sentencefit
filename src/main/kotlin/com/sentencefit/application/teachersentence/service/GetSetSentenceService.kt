package com.sentencefit.application.teachersentence.service

import com.sentencefit.application.teachersentence.dto.SetSentenceResponse
import com.sentencefit.application.teachersentence.port.`in`.GetSetSentenceUseCase
import com.sentencefit.application.teachersentence.port.out.LoadSetSentencePort
import com.sentencefit.application.teacherset.port.out.LoadSetPort
import com.sentencefit.domain.teachersentence.exception.SetSentenceErrorCode
import com.sentencefit.domain.teachersentence.exception.SetSentenceException
import com.sentencefit.domain.teacherset.exception.SetErrorCode
import com.sentencefit.domain.teacherset.exception.SetException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetSetSentenceService(
    private val loadSetPort: LoadSetPort,
    private val loadSetSentencePort: LoadSetSentencePort,
) : GetSetSentenceUseCase {

    @Transactional(readOnly = true)
    override fun get(
        teacherId: Long,
        classId: Long,
        setId: Long,
        sentenceId: Long,
    ): SetSentenceResponse {
        loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        val sentence = loadSetSentencePort.findBySetIdAndId(setId, sentenceId)
            ?: throw SetSentenceException(SetSentenceErrorCode.SET_SENTENCE_NOT_FOUND)

        return SetSentenceDtoMapper.toResponse(sentence)
    }

    @Transactional(readOnly = true)
    override fun getList(
        teacherId: Long,
        classId: Long,
        setId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<SetSentenceResponse> {
        loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        return loadSetSentencePort.findAllBySetId(
            setId = setId,
            keyword = keyword?.trim(),
            pageable = pageable,
        ).map(SetSentenceDtoMapper::toResponse)
    }
}