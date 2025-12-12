package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.domain.classjoin.model.ClassMemberStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClassMemberJpaRepository : JpaRepository<ClassMemberJpaEntity, Long> {
    fun existsByClassIdAndStudentIdAndStatus(
        classId: Long,
        studentId: Long,
        status: ClassMemberStatus,
    ): Boolean

    fun findByClassIdAndStudentIdAndStatus(
        classId: Long,
        studentId: Long,
        status: ClassMemberStatus,
    ): ClassMemberJpaEntity?

    @Query(
        """
        select cm
        from ClassMemberJpaEntity cm
        join UserJpaEntity u on u.id = cm.studentId
        where cm.classId = :classId
          and cm.status = com.sentencefit.domain.classjoin.model.ClassMemberStatus.ACTIVE
          and (
              :keyword is null
              or :keyword = ''
              or lower(u.name) like lower(concat('%', :keyword, '%'))
              or lower(u.email) like lower(concat('%', :keyword, '%'))
          )
        """
    )
    fun findMembersWithUser(
        classId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassMemberJpaEntity>
}