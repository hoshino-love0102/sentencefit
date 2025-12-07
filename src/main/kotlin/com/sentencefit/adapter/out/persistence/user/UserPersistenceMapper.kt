package com.sentencefit.adapter.out.persistence.user

import com.sentencefit.domain.user.model.User

object UserPersistenceMapper {

    fun toDomain(entity: UserJpaEntity): User =
        User(
            id = entity.id,
            email = entity.email,
            password = entity.password,
            name = entity.name,
            role = entity.role
        )

    fun toEntity(domain: User): UserJpaEntity =
        UserJpaEntity(
            id = domain.id,
            email = domain.email,
            password = domain.password,
            name = domain.name,
            role = domain.role
        )
}