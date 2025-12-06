package com.sentencefit.application.auth.port.out

interface JwtIssuePort {
    fun generateAccessToken(userId: Long, email: String, role: String): String
    fun generateRefreshToken(userId: Long): String
    fun extractUserId(token: String): Long
    fun validate(token: String): Boolean
    fun accessTokenExpirationSeconds(): Long
    fun refreshTokenExpirationSeconds(): Long
}