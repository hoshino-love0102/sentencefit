package com.sentencefit.application.auth.service

import com.sentencefit.application.auth.port.`in`.SendEmailVerificationUseCase
import com.sentencefit.application.auth.port.out.EmailVerificationPort
import com.sentencefit.application.auth.port.out.LoadUserPort
import com.sentencefit.application.auth.port.out.MailSendPort
import com.sentencefit.domain.auth.exception.AuthErrorCode
import com.sentencefit.domain.auth.exception.AuthException
import com.sentencefit.domain.auth.model.EmailVerification
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class SendEmailVerificationService(
    private val loadUserPort: LoadUserPort,
    private val emailVerificationPort: EmailVerificationPort,
    private val mailSendPort: MailSendPort
) : SendEmailVerificationUseCase {

    companion object {
        private const val EMAIL_CODE_TTL_SECONDS = 300L
    }

    override fun sendCode(email: String) {
        if (loadUserPort.existsByEmail(email)) {
            throw AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS)
        }

        val code = Random.nextInt(100000, 999999).toString()

        emailVerificationPort.save(
            EmailVerification(email = email, code = code),
            EMAIL_CODE_TTL_SECONDS
        )

        mailSendPort.send(
            to = email,
            subject = "[Sentence Fit] 이메일 인증 코드",
            text = """
                인증코드는 [$code] 입니다.
                5분 안에 입력해 주세요.
            """.trimIndent()
        )
    }
}