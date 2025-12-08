package com.sentencefit.adapter.out.redis

import com.sentencefit.application.auth.port.out.RefreshTokenPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisRefreshTokenAdapter(
    private val redisTemplate: StringRedisTemplate
) : RefreshTokenPort {

    private fun key(userId: Long) = "auth:refresh:$userId"

    override fun save(userId: Long, refreshToken: String, ttlSeconds: Long) {
        redisTemplate.opsForValue().set(
            key(userId),
            refreshToken,
            Duration.ofSeconds(ttlSeconds)
        )
    }

    override fun find(userId: Long): String? =
        redisTemplate.opsForValue().get(key(userId))

    override fun delete(userId: Long) {
        redisTemplate.delete(key(userId))
    }
}