package com.sentencefit.application.classjoin.port.out

import com.sentencefit.domain.user.model.User

interface LoadUserPort {
    fun findById(userId: Long): User?
}