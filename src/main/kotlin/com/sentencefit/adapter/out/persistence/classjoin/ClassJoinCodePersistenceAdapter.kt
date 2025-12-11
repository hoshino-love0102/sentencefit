package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.application.classjoin.port.out.LoadJoinCodePort
import com.sentencefit.application.classjoin.port.out.SaveJoinCodePort
import com.sentencefit.domain.classjoin.model.ClassJoinCode
import org.springframework.stereotype.Component

@Component
class ClassJoinCodePersistenceAdapter(
    private val classJoinCodeJpaRepository: ClassJoinCodeJpaRepository,
) : LoadJoinCodePort, SaveJoinCodePort {

    override fun findByClassId(classId: Long): ClassJoinCode? =
        classJoinCodeJpaRepository.findByClassId(classId)
            ?.let(ClassJoinCodePersistenceMapper::toDomain)

    override fun findByCode(code: String): ClassJoinCode? =
        classJoinCodeJpaRepository.findByCode(code)
            ?.let(ClassJoinCodePersistenceMapper::toDomain)

    override fun save(classJoinCode: ClassJoinCode): ClassJoinCode {
        val saved = classJoinCodeJpaRepository.save(
            ClassJoinCodePersistenceMapper.toEntity(classJoinCode)
        )
        return ClassJoinCodePersistenceMapper.toDomain(saved)
    }
}