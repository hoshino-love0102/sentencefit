package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.domain.classjoin.model.ClassMemberStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "class_members",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_class_members_class_student", columnNames = ["class_id", "student_id"]),
    ]
)
class ClassMemberJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "class_id", nullable = false)
    val classId: Long,

    @Column(name = "student_id", nullable = false)
    val studentId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: ClassMemberStatus = ClassMemberStatus.ACTIVE,

    @Column(name = "joined_at", nullable = false)
    val joinedAt: LocalDateTime = LocalDateTime.now(),
)