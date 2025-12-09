package com.sentencefit.application.teacherset.port.out

import com.sentencefit.domain.teacherset.model.StudySet

interface SaveSetPort {
    fun save(studySet: StudySet): StudySet
}