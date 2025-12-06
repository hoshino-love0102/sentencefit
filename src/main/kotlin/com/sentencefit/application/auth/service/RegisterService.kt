package com.sentencefit.application.auth.service

import com.sentencefit.application.auth.dto.RegisterRequest
import com.sentencefit.application.auth.port.`in`.RegisterUseCase
import com.sentencefit.application.auth.port.out.EmailVerificationPort
import com.sentencefit.application.auth.port.out.LoadUserPort
import com.sentencefit.application.auth.port.out.PasswordEncodePort
import com.sentencefit.application.auth.port.out.SaveUserPort
import com.sentencefit.domain.auth.exception.AuthErrorCode
import com.sentencefit.domain.auth.exception.AuthException
import com.sentencefit.domain.user.model.User
import com.sentencefit.domain.user.model.UserRole
import org.springframework.stereotype.Service

@Service
class RegisterService(
    private val loadUserPort: LoadUserPort,
    private val saveUserPort: SaveUserPort,
    private val passwordEncodePort: PasswordEncodePort,
    private val emailVerificationPort: EmailVerificationPort
) : RegisterUseCase {

    override fun register(request: RegisterRequest) {
        if (loadUserPort.existsByEmail(request.email)) {
            throw AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS)
        }

        if (!emailVerificationPort.isVerified(request.email)) {
            throw AuthException(AuthErrorCode.EMAIL_NOT_VERIFIED)
        }

        val user = User(
            email = request.email,
            password = passwordEncodePort.encode(request.password),
            name = request.name,
            role = UserRole.STUDENT
        )

        saveUserPort.save(user)
        emailVerificationPort.delete(request.email)
    }
}