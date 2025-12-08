package com.sentencefit.adapter.out.security

import com.sentencefit.application.auth.port.out.PasswordEncodePort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderAdapter(
    private val passwordEncoder: PasswordEncoder
) : PasswordEncodePort {

    override fun encode(rawPassword: String): String =
        passwordEncoder.encode(rawPassword)

    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        passwordEncoder.matches(rawPassword, encodedPassword)
}