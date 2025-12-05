package com.sentencefit.domain.user.model

data class User(
    val id: Long? = null,
    val email: String,
    val password: String,
    val name: String,
    val role: UserRole = UserRole.STUDENT
)