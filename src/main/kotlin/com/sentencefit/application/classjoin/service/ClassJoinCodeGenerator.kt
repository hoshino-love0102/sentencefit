package com.sentencefit.application.classjoin.service

import java.security.SecureRandom

object ClassJoinCodeGenerator {
    private const val CODE_LENGTH = 8
    private const val CHAR_POOL = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
    private val random = SecureRandom()

    fun generate(): String =
        buildString(CODE_LENGTH) {
            repeat(CODE_LENGTH) {
                append(CHAR_POOL[random.nextInt(CHAR_POOL.length)])
            }
        }
}