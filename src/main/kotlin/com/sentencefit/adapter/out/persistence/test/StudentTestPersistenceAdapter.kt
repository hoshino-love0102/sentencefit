package com.sentencefit.adapter.out.persistence.test

import com.sentencefit.application.test.port.out.LoadStudentTestPort
import com.sentencefit.application.test.port.out.LoadStudentTestQuestionPort
import com.sentencefit.application.test.port.out.LoadStudentTestStepResultPort
import com.sentencefit.application.test.port.out.SaveStudentTestPort
import com.sentencefit.application.test.port.out.SaveStudentTestQuestionPort
import com.sentencefit.application.test.port.out.SaveStudentTestStepResultPort
import com.sentencefit.domain.test.model.StudentTest
import com.sentencefit.domain.test.model.StudentTestQuestion
import com.sentencefit.domain.test.model.StudentTestStepResult
import org.springframework.stereotype.Component

@Component
class StudentTestPersistenceAdapter(
    private val studentTestJpaRepository: StudentTestJpaRepository,
    private val studentTestQuestionJpaRepository: StudentTestQuestionJpaRepository,
    private val studentTestStepResultJpaRepository: StudentTestStepResultJpaRepository,
    private val mapper: StudentTestPersistenceMapper,
) : SaveStudentTestPort,
    LoadStudentTestPort,
    SaveStudentTestQuestionPort,
    LoadStudentTestQuestionPort,
    SaveStudentTestStepResultPort,
    LoadStudentTestStepResultPort {

    override fun saveTest(test: StudentTest): StudentTest =
        mapper.toDomain(
            studentTestJpaRepository.save(mapper.toEntity(test))
        )

    override fun loadTestById(testId: Long): StudentTest? =
        studentTestJpaRepository.findById(testId)
            .map(mapper::toDomain)
            .orElse(null)

    override fun saveQuestions(questions: List<StudentTestQuestion>): List<StudentTestQuestion> =
        studentTestQuestionJpaRepository.saveAll(questions.map(mapper::toEntity))
            .map(mapper::toDomain)

    override fun loadQuestionByTestIdAndQuestionNo(testId: Long, questionNo: Int): StudentTestQuestion? =
        studentTestQuestionJpaRepository.findByTestIdAndQuestionNo(testId, questionNo)
            ?.let(mapper::toDomain)

    override fun loadQuestionById(questionId: Long): StudentTestQuestion? =
        studentTestQuestionJpaRepository.findById(questionId)
            .map(mapper::toDomain)
            .orElse(null)

    override fun loadQuestionsByTestId(testId: Long): List<StudentTestQuestion> =
        studentTestQuestionJpaRepository.findAllByTestIdOrderByQuestionNoAsc(testId)
            .map(mapper::toDomain)

    override fun saveStepResult(stepResult: StudentTestStepResult): StudentTestStepResult =
        mapper.toDomain(
            studentTestStepResultJpaRepository.save(mapper.toEntity(stepResult))
        )

    override fun saveStepResults(stepResults: List<StudentTestStepResult>): List<StudentTestStepResult> =
        studentTestStepResultJpaRepository.saveAll(stepResults.map(mapper::toEntity))
            .map(mapper::toDomain)

    override fun loadStepResultByQuestionIdAndStepNo(
        questionId: Long,
        stepNo: Int,
    ): StudentTestStepResult? =
        studentTestStepResultJpaRepository.findByQuestionIdAndStepNo(questionId, stepNo)
            ?.let(mapper::toDomain)

    override fun loadStepResultsByQuestionId(questionId: Long): List<StudentTestStepResult> =
        studentTestStepResultJpaRepository.findAllByQuestionIdOrderByStepNoAsc(questionId)
            .map(mapper::toDomain)

    override fun loadStepResultsByTestId(testId: Long): List<StudentTestStepResult> =
        studentTestStepResultJpaRepository.findAllByTestIdOrderByQuestionIdAscStepNoAsc(testId)
            .map(mapper::toDomain)
}