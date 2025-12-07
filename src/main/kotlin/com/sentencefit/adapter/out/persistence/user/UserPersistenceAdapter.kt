package com.sentencefit.adapter.out.persistence.user

import com.sentencefit.application.auth.port.out.LoadUserPort
import com.sentencefit.application.auth.port.out.SaveUserPort
import com.sentencefit.application.user.port.out.LoadUserQueryPort
import com.sentencefit.domain.user.model.User
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : LoadUserPort, SaveUserPort, LoadUserQueryPort {

    override fun findByEmail(email: String): User? =
        userJpaRepository.findByEmail(email)
            .map(UserPersistenceMapper::toDomain)
            .orElse(null)

    override fun findById(id: Long): User? =
        userJpaRepository.findById(id)
            .map(UserPersistenceMapper::toDomain)
            .orElse(null)

    override fun existsByEmail(email: String): Boolean =
        userJpaRepository.existsByEmail(email)

    override fun save(user: User): User =
        UserPersistenceMapper.toDomain(
            userJpaRepository.save(UserPersistenceMapper.toEntity(user))
        )

    override fun getById(id: Long): User? = findById(id)
}