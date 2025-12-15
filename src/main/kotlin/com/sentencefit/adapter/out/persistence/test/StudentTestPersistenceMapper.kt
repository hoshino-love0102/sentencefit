package com.sentencefit.adapter.out.persistence.test

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sentencefit.domain.test.model.StudentTest
import com.sentencefit.domain.test.model.StudentTestQuestion
import com.sentencefit.domain.test.model.StudentTestStepResult
import org.springframework.stereotype.Component

@Component
class StudentTestPersistenceMapper {

    private val objectMapper = jacksonObjectMapper()

    fun toEntity(domain: StudentTest): StudentTestJpaEntity =
        StudentTestJpaEntity(
            id = domain.id,
            studentId = domain.studentId,
            setId = domain.setId,
            mode = domain.mode,
            status = domain.status,
            totalQuestions = domain.totalQuestions,
            currentQuestionNo = domain.currentQuestionNo,
            score = domain.score,
            correctCount = domain.correctCount,
            wrongCount = domain.wrongCount,
            retestCount = domain.retestCount,
            createdAt = domain.createdAt ?: java.time.LocalDateTime.now(),
            completedAt = domain.completedAt,
        )

    fun toDomain(entity: StudentTestJpaEntity): StudentTest =
        StudentTest(
            id = entity.id,
            studentId = entity.studentId,
            setId = entity.setId,
            mode = entity.mode,
            status = entity.status,
            totalQuestions = entity.totalQuestions,
            currentQuestionNo = entity.currentQuestionNo,
            score = entity.score,
            correctCount = entity.correctCount,
            wrongCount = entity.wrongCount,
            retestCount = entity.retestCount,
            createdAt = entity.createdAt,
            completedAt = entity.completedAt,
        )

    fun toEntity(domain: StudentTestQuestion): StudentTestQuestionJpaEntity =
        StudentTestQuestionJpaEntity(
            id = domain.id,
            testId = domain.testId,
            sentenceId = domain.sentenceId,
            questionNo = domain.questionNo,
            englishText = domain.englishText,
            koreanText = domain.koreanText,
            grammarPoint = domain.grammarPoint,
            tokensJson = objectMapper.writeValueAsString(domain.tokens),
        )

    fun toDomain(entity: StudentTestQuestionJpaEntity): StudentTestQuestion =
        StudentTestQuestion(
            id = entity.id,
            testId = entity.testId,
            sentenceId = entity.sentenceId,
            questionNo = entity.questionNo,
            englishText = entity.englishText,
            koreanText = entity.koreanText,
            grammarPoint = entity.grammarPoint,
            tokens = objectMapper.readValue(entity.tokensJson, object : TypeReference<List<String>>() {}),
        )

    fun toEntity(domain: StudentTestStepResult): StudentTestStepResultJpaEntity =
        StudentTestStepResultJpaEntity(
            id = domain.id,
            testId = domain.testId,
            questionId = domain.questionId,
            stepNo = domain.stepNo,
            stepType = domain.stepType,
            answerType = domain.answerType,
            submittedAnswerText = domain.submittedAnswerText,
            submittedAnswerTokensJson = domain.submittedAnswerTokens?.let { objectMapper.writeValueAsString(it) },
            isCorrect = domain.isCorrect,
            correctAnswer = domain.correctAnswer,
            explanation = domain.explanation,
            errorType = domain.errorType,
            usedAi = domain.usedAi,
            canRetest = domain.canRetest,
            createdAt = domain.createdAt ?: java.time.LocalDateTime.now(),
        )

    fun toDomain(entity: StudentTestStepResultJpaEntity): StudentTestStepResult =
        StudentTestStepResult(
            id = entity.id,
            testId = entity.testId,
            questionId = entity.questionId,
            stepNo = entity.stepNo,
            stepType = entity.stepType,
            answerType = entity.answerType,
            submittedAnswerText = entity.submittedAnswerText,
            submittedAnswerTokens = entity.submittedAnswerTokensJson?.let {
                objectMapper.readValue(it, object : TypeReference<List<String>>() {})
            },
            isCorrect = entity.isCorrect,
            correctAnswer = entity.correctAnswer,
            explanation = entity.explanation,
            errorType = entity.errorType,
            usedAi = entity.usedAi,
            canRetest = entity.canRetest,
            createdAt = entity.createdAt,
        )
}