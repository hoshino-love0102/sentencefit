package com.sentencefit.application.classjoin.port.out

import com.sentencefit.domain.classjoin.model.ClassJoinCode

interface SaveJoinCodePort {
    fun save(classJoinCode: ClassJoinCode): ClassJoinCode
}