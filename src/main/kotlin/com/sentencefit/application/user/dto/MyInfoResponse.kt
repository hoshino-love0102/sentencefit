package com.sentencefit.application.user.dto

import com.sentencefit.domain.user.model.User

data class MyInfoResponse(
    val id: Long,
    val email: String,
    val name: String,
    val role: String
) {
    companion object {
        fun from(user: User): MyInfoResponse =
            MyInfoResponse(
                id = user.id!!,
                email = user.email,
                name = user.name,
                role = user.role.name
            )
    }
}