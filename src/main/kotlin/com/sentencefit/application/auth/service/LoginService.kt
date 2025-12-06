package com.sentencefit.application.auth.service

import com.sentencefit.application.auth.dto.LoginRequest
import com.sentencefit.application.auth.dto.TokenResponse
import com.sentencefit.application.auth.port.`in`.LoginUseCase
import com.sentencefit.application.auth.port.out.JwtIssuePort
import com.sentencefit.application.auth.port.out.LoadUserPort
import com.sentencefit.application.auth.port.out.PasswordEncodePort
import com.sentencefit.application.auth.port.out.RefreshTokenPort
import com.sentencefit.domain.auth.exception.AuthErrorCode
import com.sentencefit.domain.auth.exception.AuthException
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val loadUserPort: LoadUserPort,
    private val passwordEncodePort: PasswordEncodePort,
    private val jwtIssuePort: JwtIssuePort,
    private val refreshTokenPort: RefreshTokenPort
) : LoginUseCase {

    override fun login(request: LoginRequest): TokenResponse {
        val user = loadUserPort.findByEmail(request.email)
            ?: throw AuthException(AuthErrorCode.INVALID_CREDENTIALS)

        if (!passwordEncodePort.matches(request.password, user.password)) {
            throw AuthException(AuthErrorCode.INVALID_CREDENTIALS)
        }

        val accessToken = jwtIssuePort.generateAccessToken(
            userId = user.id!!,
            email = user.email,
            role = user.role.name
        )

        val refreshToken = jwtIssuePort.generateRefreshToken(user.id)

        refreshTokenPort.save(
            userId = user.id,
            refreshToken = refreshToken,
            ttlSeconds = jwtIssuePort.refreshTokenExpirationSeconds()
        )

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtIssuePort.accessTokenExpirationSeconds()
        )
    }
}