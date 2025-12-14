package com.sentencefit.application.test.port.out

import com.sentencefit.domain.test.model.StudentTestStepResult

interface SaveStudentTestStepResultPort {
    fun saveStepResult(stepResult: StudentTestStepResult): StudentTestStepResult
    fun saveStepResults(stepResults: List<StudentTestStepResult>): List<StudentTestStepResult>
}