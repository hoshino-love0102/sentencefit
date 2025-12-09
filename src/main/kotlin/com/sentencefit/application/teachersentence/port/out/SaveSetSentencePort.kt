package com.sentencefit.application.teachersentence.port.out

import com.sentencefit.domain.teachersentence.model.SetSentence

interface SaveSetSentencePort {
    fun save(sentence: SetSentence): SetSentence
    fun saveAll(sentences: List<SetSentence>): List<SetSentence>
}