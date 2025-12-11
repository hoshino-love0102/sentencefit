package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.application.classjoin.port.out.LoadClassMemberPort
import com.sentencefit.application.classjoin.port.out.SaveClassMemberPort
import com.sentencefit.domain.classjoin.model.ClassMember
import com.sentencefit.domain.classjoin.model.ClassMemberStatus
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ClassMemberPersistenceAdapter(
    private val classMemberJpaRepository: ClassMemberJpaRepository,
) : LoadClassMemberPort, SaveClassMemberPort {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun existsActiveMember(classId: Long, studentId: Long): Boolean =
        classMemberJpaRepository.existsByClassIdAndStudentIdAndStatus(
            classId = classId,
            studentId = studentId,
            status = ClassMemberStatus.ACTIVE,
        )

    override fun save(classMember: ClassMember): ClassMember {
        log.info(
            "Saving class member. classId={}, studentId={}, status={}, joinedAt={}",
            classMember.classId,
            classMember.studentId,
            classMember.status,
            classMember.joinedAt,
        )

        val entity = ClassMemberPersistenceMapper.toEntity(classMember)

        log.info(
            "Mapped entity. id={}, classId={}, studentId={}, status={}, joinedAt={}",
            entity.id,
            entity.classId,
            entity.studentId,
            entity.status,
            entity.joinedAt,
        )

        val saved = classMemberJpaRepository.save(entity)

        log.info(
            "Saved entity. id={}, classId={}, studentId={}, status={}, joinedAt={}",
            saved.id,
            saved.classId,
            saved.studentId,
            saved.status,
            saved.joinedAt,
        )

        return ClassMemberPersistenceMapper.toDomain(saved)
    }
}