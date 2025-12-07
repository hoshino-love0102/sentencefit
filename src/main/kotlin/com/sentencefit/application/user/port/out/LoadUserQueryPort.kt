package com.sentencefit.application.user.port.out

import com.sentencefit.domain.user.model.User

interface LoadUserQueryPort {
    fun getById(id: Long): User?
}