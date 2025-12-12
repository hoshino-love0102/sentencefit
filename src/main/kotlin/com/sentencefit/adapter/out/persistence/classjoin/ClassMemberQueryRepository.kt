package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.application.classjoin.dto.ClassMemberResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ClassMemberQueryRepository(
    @PersistenceContext
    private val entityManager: EntityManager,
) {

    fun findMembers(
        classId: Long,
        keyword: String?,
        pageable: Pageable,
    ): Page<ClassMemberResponse> {
        val normalizedKeyword = keyword?.trim()?.takeIf { it.isNotBlank() }

        val baseWhere = StringBuilder(
            """
            FROM class_members cm
            JOIN users u ON u.id = cm.student_id
            WHERE cm.class_id = :classId
              AND cm.status = 'ACTIVE'
            """.trimIndent()
        )

        if (normalizedKeyword != null) {
            baseWhere.append(
                """
                
                  AND (
                    LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  )
                """.trimIndent()
            )
        }

        val contentSql =
            """
            SELECT 
                u.id AS studentId,
                u.name AS name,
                u.email AS email,
                cm.joined_at AS joinedAt,
                cm.status AS status
            $baseWhere
            ORDER BY cm.joined_at DESC
            LIMIT :limit OFFSET :offset
            """.trimIndent()

        val countSql =
            """
            SELECT COUNT(*)
            $baseWhere
            """.trimIndent()

        val contentQuery = entityManager.createNativeQuery(contentSql)
        val countQuery = entityManager.createNativeQuery(countSql)

        contentQuery.setParameter("classId", classId)
        countQuery.setParameter("classId", classId)

        if (normalizedKeyword != null) {
            contentQuery.setParameter("keyword", normalizedKeyword)
            countQuery.setParameter("keyword", normalizedKeyword)
        }

        contentQuery.setParameter("limit", pageable.pageSize)
        contentQuery.setParameter("offset", pageable.offset)

        @Suppress("UNCHECKED_CAST")
        val rows = contentQuery.resultList as List<Array<Any?>>
        val total = (countQuery.singleResult as Number).toLong()

        val content = rows.map {
            ClassMemberResponse(
                studentId = (it[0] as Number).toLong(),
                name = it[1] as String,
                email = it[2] as String,
                joinedAt = (it[3] as java.sql.Timestamp).toLocalDateTime(),
                status = it[4] as String,
            )
        }

        return PageImpl(content, pageable, total)
    }
}