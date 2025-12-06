package com.sentencefit.application.auth.port.out

import com.sentencefit.domain.user.model.User

interface SaveUserPort {
    fun save(user: User): User
}