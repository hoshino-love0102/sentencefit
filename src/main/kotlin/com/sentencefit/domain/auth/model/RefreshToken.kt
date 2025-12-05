package com.sentencefit.domain.auth.model

data class RefreshToken(
    val userId: Long,
    val token: String
)