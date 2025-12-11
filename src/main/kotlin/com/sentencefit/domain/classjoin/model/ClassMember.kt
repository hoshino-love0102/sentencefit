package com.sentencefit.domain.classjoin.model

import java.time.LocalDateTime

data class ClassMember(
    val id: Long? = null,
    val classId: Long,
    val studentId: Long,
    val status: ClassMemberStatus = ClassMemberStatus.ACTIVE,
    val joinedAt: LocalDateTime? = null,
)