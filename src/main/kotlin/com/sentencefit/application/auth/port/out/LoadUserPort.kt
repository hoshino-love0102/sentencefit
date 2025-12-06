package com.sentencefit.application.auth.port.out

import com.sentencefit.domain.user.model.User

interface LoadUserPort {
    fun findByEmail(email: String): User?
    fun findById(id: Long): User?
    fun existsByEmail(email: String): Boolean
}