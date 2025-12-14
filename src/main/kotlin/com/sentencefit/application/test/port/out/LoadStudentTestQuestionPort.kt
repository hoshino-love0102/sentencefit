package com.sentencefit.application.test.port.out

import com.sentencefit.domain.test.model.StudentTestQuestion

interface LoadStudentTestQuestionPort {
    fun loadQuestionById(questionId: Long): StudentTestQuestion?
    fun loadQuestionByTestIdAndQuestionNo(testId: Long, questionNo: Int): StudentTestQuestion?
    fun loadQuestionsByTestId(testId: Long): List<StudentTestQuestion>
}