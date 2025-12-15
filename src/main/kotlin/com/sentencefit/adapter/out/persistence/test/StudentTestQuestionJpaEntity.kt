package com.sentencefit.adapter.out.persistence.test

import jakarta.persistence.*

@Entity
@Table(name = "student_test_question")
class StudentTestQuestionJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "test_id", nullable = false)
    val testId: Long,

    @Column(name = "sentence_id", nullable = false)
    val sentenceId: Long,

    @Column(name = "question_no", nullable = false)
    val questionNo: Int,

    @Column(name = "english_text", nullable = false, columnDefinition = "TEXT")
    val englishText: String,

    @Column(name = "korean_text", columnDefinition = "TEXT")
    val koreanText: String? = null,

    @Column(name = "grammar_point", columnDefinition = "TEXT")
    val grammarPoint: String? = null,

    @Column(name = "tokens_json", nullable = false, columnDefinition = "TEXT")
    val tokensJson: String,
)