package com.sentencefit.application.teachersentence.service

import com.sentencefit.application.teachersentence.dto.UpdateSentenceOrderRequest
import com.sentencefit.application.teachersentence.port.`in`.UpdateSentenceOrderUseCase
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
class UpdateSentenceOrderService(
    private val loadSetPort: LoadSetPort,
    private val loadSetSentencePort: LoadSetSentencePort,
    private val saveSetSentencePort: SaveSetSentencePort,
) : UpdateSentenceOrderUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
        request: UpdateSentenceOrderRequest,
    ) {
        loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        val currentSentences = loadSetSentencePort.findAllActiveBySetId(setId)
        val sentenceMap = currentSentences.associateBy { it.id }

        val updatedSentences = request.items.map { item ->
            val sentence = sentenceMap[item.sentenceId]
                ?: throw SetSentenceException(SetSentenceErrorCode.SET_SENTENCE_NOT_FOUND)

            sentence.updateOrder(item.orderNo)
        }

        saveSetSentencePort.saveAll(updatedSentences)
    }
}