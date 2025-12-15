package com.sentencefit.adapter.out.persistence.test

import com.sentencefit.domain.test.model.TestMode
import com.sentencefit.domain.test.model.TestStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "student_test")
class StudentTestJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "student_id", nullable = false)
    val studentId: Long,

    @Column(name = "set_id", nullable = false)
    val setId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false, length = 20)
    val mode: TestMode,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    val status: TestStatus,

    @Column(name = "total_questions", nullable = false)
    val totalQuestions: Int,

    @Column(name = "current_question_no", nullable = false)
    val currentQuestionNo: Int,

    @Column(name = "score", nullable = false)
    val score: Int,

    @Column(name = "correct_count", nullable = false)
    val correctCount: Int,

    @Column(name = "wrong_count", nullable = false)
    val wrongCount: Int,

    @Column(name = "retest_count", nullable = false)
    val retestCount: Int,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "completed_at")
    val completedAt: LocalDateTime? = null,
)