package com.sentencefit.domain.classjoin.model

data class ClassMember(
    val id: Long? = null,
    val classId: Long,
    val studentId: Long,
    val status: ClassMemberStatus = ClassMemberStatus.ACTIVE,
    val joinedAt: java.time.LocalDateTime? = null,
) {
    fun remove(): ClassMember = copy(
        status = ClassMemberStatus.REMOVED,
    )
}