package com.sentencefit.application.auth.port.out

interface PasswordEncodePort {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}