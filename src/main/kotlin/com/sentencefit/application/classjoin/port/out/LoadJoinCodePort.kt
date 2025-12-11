package com.sentencefit.application.classjoin.port.out

import com.sentencefit.domain.classjoin.model.ClassJoinCode

interface LoadJoinCodePort {
    fun findByClassId(classId: Long): ClassJoinCode?
    fun findByCode(code: String): ClassJoinCode?
}