package com.sentencefit.application.test.port.out

import com.sentencefit.domain.test.model.StudentTest

interface SaveStudentTestPort {
    fun saveTest(test: StudentTest): StudentTest
}