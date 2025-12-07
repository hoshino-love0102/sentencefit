package com.sentencefit.application.user.service

import com.sentencefit.application.user.port.`in`.GetMyInfoUseCase
import com.sentencefit.application.user.port.out.LoadUserQueryPort
import com.sentencefit.domain.user.exception.UserErrorCode
import com.sentencefit.domain.user.exception.UserException
import com.sentencefit.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class GetMyInfoService(
    private val loadUserQueryPort: LoadUserQueryPort
) : GetMyInfoUseCase {

    override fun getById(userId: Long): User {
        return loadUserQueryPort.getById(userId)
            ?: throw UserException(UserErrorCode.USER_NOT_FOUND)
    }
}