package com.sentencefit.application.user.port.`in`

import com.sentencefit.domain.user.model.User

interface GetMyInfoUseCase {
    fun getById(userId: Long): User
}