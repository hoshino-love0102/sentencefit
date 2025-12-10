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
        val studySet = loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        if (studySet.status.name == "DELETED") {
            throw SetException(SetErrorCode.SET_ALREADY_DELETED)
        }

        if (request.items.isEmpty()) {
            throw SetSentenceException(SetSentenceErrorCode.EMPTY_SENTENCE_ORDER_ITEMS)
        }

        request.items.forEach { item ->
            if (item.sentenceId <= 0) {
                throw SetSentenceException(SetSentenceErrorCode.SENTENCE_ID_REQUIRED)
            }
            if (item.orderNo <= 0) {
                throw SetSentenceException(SetSentenceErrorCode.SENTENCE_ORDER_MUST_BE_POSITIVE)
            }
        }

        val duplicateOrderExists = request.items
            .groupBy { it.orderNo }
            .any { it.value.size > 1 }

        if (duplicateOrderExists) {
            throw SetSentenceException(SetSentenceErrorCode.DUPLICATE_SENTENCE_ORDER)
        }

        val currentSentences = loadSetSentencePort.findAllActiveBySetId(setId)
        val sentenceMap = currentSentences.associateBy { it.id }

        val requestIds = request.items.map { it.sentenceId }.toSet()
        val currentIds = currentSentences.mapNotNull { it.id }.toSet()

        if (requestIds != currentIds) {
            throw SetSentenceException(SetSentenceErrorCode.INVALID_SENTENCE_ORDER)
        }

        val updatedSentences = request.items.map { item ->
            val sentence = sentenceMap[item.sentenceId]
                ?: throw SetSentenceException(SetSentenceErrorCode.SET_SENTENCE_NOT_FOUND)

            if (sentence.status.name == "DELETED") {
                throw SetSentenceException(SetSentenceErrorCode.SET_SENTENCE_ALREADY_DELETED)
            }

            sentence.updateOrder(item.orderNo)
        }

        saveSetSentencePort.saveAll(updatedSentences)
    }
}