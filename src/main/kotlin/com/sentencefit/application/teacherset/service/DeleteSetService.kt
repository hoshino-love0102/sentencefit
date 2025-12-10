package com.sentencefit.application.teacherset.service

import com.sentencefit.application.teacherset.port.`in`.DeleteSetUseCase
import com.sentencefit.application.teacherset.port.out.LoadSetPort
import com.sentencefit.application.teacherset.port.out.SaveSetPort
import com.sentencefit.domain.teacherset.exception.SetErrorCode
import com.sentencefit.domain.teacherset.exception.SetException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteSetService(
    private val loadSetPort: LoadSetPort,
    private val saveSetPort: SaveSetPort,
) : DeleteSetUseCase {

    @Transactional
    override fun execute(
        teacherId: Long,
        classId: Long,
        setId: Long,
    ) {
        val studySet = loadSetPort.findByTeacherIdAndClassIdAndId(teacherId, classId, setId)
            ?: throw SetException(SetErrorCode.SET_NOT_FOUND)

        if (studySet.status.name == "DELETED") {
            throw SetException(SetErrorCode.SET_ALREADY_DELETED)
        }

        saveSetPort.save(studySet.delete())
    }
}