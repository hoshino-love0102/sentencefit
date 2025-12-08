package com.sentencefit.adapter.out.redis

import com.sentencefit.application.auth.port.out.EmailVerificationPort
import com.sentencefit.domain.auth.model.EmailVerification
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisVerificationAdapter(
    private val redisTemplate: StringRedisTemplate
) : EmailVerificationPort {

    private fun codeKey(email: String) = "auth:email:code:$email"
    private fun verifiedKey(email: String) = "auth:email:verified:$email"

    override fun save(emailVerification: EmailVerification, ttlSeconds: Long) {
        redisTemplate.opsForValue().set(
            codeKey(emailVerification.email),
            emailVerification.code,
            Duration.ofSeconds(ttlSeconds)
        )
    }

    override fun find(email: String): EmailVerification? {
        val code = redisTemplate.opsForValue().get(codeKey(email)) ?: return null
        return EmailVerification(email = email, code = code, verified = false)
    }

    override fun markVerified(email: String, ttlSeconds: Long) {
        redisTemplate.delete(codeKey(email))
        redisTemplate.opsForValue().set(
            verifiedKey(email),
            "true",
            Duration.ofSeconds(ttlSeconds)
        )
    }

    override fun isVerified(email: String): Boolean {
        return redisTemplate.opsForValue().get(verifiedKey(email)) == "true"
    }

    override fun delete(email: String) {
        redisTemplate.delete(codeKey(email))
        redisTemplate.delete(verifiedKey(email))
    }
}