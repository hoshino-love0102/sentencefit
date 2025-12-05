package com.sentencefit.domain.auth.model

data class EmailVerification(
    val email: String,
    val code: String,
    val verified: Boolean = false
)