package com.sentencefit.adapter.out.persistence.test

import com.sentencefit.domain.test.model.AnswerType
import com.sentencefit.domain.test.model.ErrorType
import com.sentencefit.domain.test.model.StepType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "student_test_step_result",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_student_test_step_result_question_step",
            columnNames = ["question_id", "step_no"]
        )
    ]
)
class StudentTestStepResultJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "test_id", nullable = false)
    val testId: Long,

    @Column(name = "question_id", nullable = false)
    val questionId: Long,

    @Column(name = "step_no", nullable = false)
    val stepNo: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "step_type", nullable = false, length = 30)
    val stepType: StepType,

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_type", nullable = false, length = 20)
    val answerType: AnswerType,

    @Column(name = "submitted_answer_text", columnDefinition = "TEXT")
    val submittedAnswerText: String? = null,

    @Column(name = "submitted_answer_tokens_json", columnDefinition = "TEXT")
    val submittedAnswerTokensJson: String? = null,

    @Column(name = "is_correct", nullable = false)
    val isCorrect: Boolean,

    @Column(name = "correct_answer", nullable = false, columnDefinition = "TEXT")
    val correctAnswer: String,

    @Column(name = "explanation", columnDefinition = "TEXT")
    val explanation: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "error_type", length = 20)
    val errorType: ErrorType? = null,

    @Column(name = "used_ai", nullable = false)
    val usedAi: Boolean,

    @Column(name = "can_retest", nullable = false)
    val canRetest: Boolean,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)