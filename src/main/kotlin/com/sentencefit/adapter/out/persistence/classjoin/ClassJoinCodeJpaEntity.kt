package com.sentencefit.adapter.out.persistence.classjoin

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "class_join_codes",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_class_join_codes_class_id", columnNames = ["class_id"]),
        UniqueConstraint(name = "uk_class_join_codes_code", columnNames = ["code"]),
    ]
)
class ClassJoinCodeJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "class_id", nullable = false)
    val classId: Long,

    @Column(nullable = false, length = 20)
    var code: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)