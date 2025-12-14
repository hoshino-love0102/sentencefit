package com.sentencefit.application.test.port.out

import com.sentencefit.domain.test.model.StudentTestStepResult

interface LoadStudentTestStepResultPort {
    fun loadStepResultByQuestionIdAndStepNo(questionId: Long, stepNo: Int): StudentTestStepResult?
    fun loadStepResultsByQuestionId(questionId: Long): List<StudentTestStepResult>
    fun loadStepResultsByTestId(testId: Long): List<StudentTestStepResult>
}