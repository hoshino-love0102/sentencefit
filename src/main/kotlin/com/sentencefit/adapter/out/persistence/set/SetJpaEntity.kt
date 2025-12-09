package com.sentencefit.adapter.out.persistence.set

import com.sentencefit.domain.teacherset.model.SetStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sets")
class SetJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "class_id", nullable = false)
    val classId: Long,

    @Column(name = "teacher_id", nullable = false)
    val teacherId: Long,

    @Column(nullable = false, length = 150)
    var title: String,

    @Column(length = 1000)
    var description: String? = null,

    @Column(name = "is_published", nullable = false)
    var isPublished: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: SetStatus = SetStatus.ACTIVE,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,
)