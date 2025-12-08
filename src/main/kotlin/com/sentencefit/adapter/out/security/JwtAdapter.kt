package com.sentencefit.adapter.out.security

import com.sentencefit.application.auth.port.out.JwtIssuePort
import com.sentencefit.common.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date

@Component
class JwtAdapter(
    private val jwtProperties: JwtProperties
) : JwtIssuePort {

    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    override fun generateAccessToken(userId: Long, email: String, role: String): String {
        val now = Date()
        val expiry = Date(now.time + jwtProperties.accessTokenExpirationSeconds * 1000)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("email", email)
            .claim("role", role)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)
            .compact()
    }

    override fun generateRefreshToken(userId: Long): String {
        val now = Date()
        val expiry = Date(now.time + jwtProperties.refreshTokenExpirationSeconds * 1000)

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)
            .compact()
    }

    override fun extractUserId(token: String): Long =
        parseClaims(token).subject.toLong()

    override fun validate(token: String): Boolean =
        runCatching { parseClaims(token); true }.getOrDefault(false)

    override fun accessTokenExpirationSeconds(): Long =
        jwtProperties.accessTokenExpirationSeconds

    override fun refreshTokenExpirationSeconds(): Long =
        jwtProperties.refreshTokenExpirationSeconds

    private fun parseClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}