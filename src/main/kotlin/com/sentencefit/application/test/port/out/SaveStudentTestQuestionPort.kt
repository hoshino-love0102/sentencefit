package com.sentencefit.application.test.port.out

import com.sentencefit.domain.test.model.StudentTestQuestion

interface SaveStudentTestQuestionPort {
    fun saveQuestions(questions: List<StudentTestQuestion>): List<StudentTestQuestion>
}