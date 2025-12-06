package com.sentencefit.application.auth.dto

data class  TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val grantType: String = "Bearer",
    val expiresIn: Long
)