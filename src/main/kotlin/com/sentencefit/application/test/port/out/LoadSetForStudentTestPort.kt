package com.sentencefit.application.test.port.out

interface LoadSetForStudentTestPort {
    fun loadSet(setId: Long): StudentTestSetSource?
    fun loadSentences(setId: Long): List<StudentTestSentenceSource>
}

data class StudentTestSetSource(
    val setId: Long,
    val classId: Long,
    val teacherId: Long,
    val title: String,
    val isPublished: Boolean,
)

data class StudentTestSentenceSource(
    val sentenceId: Long,
    val orderNo: Int,
    val englishText: String,
    val koreanText: String?,
    val grammarPoint: String?,
)