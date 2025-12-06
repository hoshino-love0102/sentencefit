package com.sentencefit.application.auth.port.out

interface RefreshTokenPort {
    fun save(userId: Long, refreshToken: String, ttlSeconds: Long)
    fun find(userId: Long): String?
    fun delete(userId: Long)
}