package com.sentencefit.adapter.out.persistence.sentence

import com.sentencefit.domain.teachersentence.model.SetSentenceStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "set_sentences")
class SetSentenceJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "set_id", nullable = false)
    val setId: Long,

    @Column(name = "order_no", nullable = false)
    var orderNo: Int,

    @Column(name = "display_code", length = 20)
    var displayCode: String? = null,

    @Column(name = "english_text", nullable = false, length = 2000)
    var englishText: String,

    @Column(name = "korean_text", length = 2000)
    var koreanText: String? = null,

    @Column(name = "grammar_point", length = 500)
    var grammarPoint: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: SetSentenceStatus = SetSentenceStatus.ACTIVE,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,
)