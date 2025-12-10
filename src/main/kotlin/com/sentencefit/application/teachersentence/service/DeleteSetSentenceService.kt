package com.sentencefit.application.teachersentence.service

import com.sentencefit.application.teachersentence.port.`in`.DeleteSetSentenceUseCase
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
class DeleteSetSentenceService(
    private val loadSetPort: LoadSetPort,
    private val loadSetSentencePort: LoadSetSentencePort,
    private val saveSetSentencePort: SaveSetSentencePort,
) : DeleteSetSentenceUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        sentenceId: Long,
    ) {
        val studySet = loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        if (studySet.status.name == "DELETED") {
            throw SetException(SetErrorCode.SET_ALREADY_DELETED)
        }

        val sentence = loadSetSentencePort.findBySetIdAndId(setId, sentenceId)
            ?: throw SetSentenceException(SetSentenceErrorCode.SET_SENTENCE_NOT_FOUND)

        if (sentence.status.name == "DELETED") {
            throw SetSentenceException(SetSentenceErrorCode.SET_SENTENCE_ALREADY_DELETED)
        }

        saveSetSentencePort.save(sentence.delete())
    }
}