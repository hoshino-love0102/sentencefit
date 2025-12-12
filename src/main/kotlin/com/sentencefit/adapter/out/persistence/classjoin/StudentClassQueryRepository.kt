package com.sentencefit.adapter.out.persistence.classjoin

import com.sentencefit.application.classjoin.dto.StudentClassDetailResponse
import com.sentencefit.application.classjoin.dto.StudentClassSetResponse
import com.sentencefit.application.classjoin.dto.StudentClassSummaryResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class StudentClassQueryRepository(
    @PersistenceContext
    private val entityManager: EntityManager,
) {

    fun findMyClasses(studentId: Long): List<StudentClassSummaryResponse> {
        val sql =
            """
            SELECT
                c.id AS class_id,
                c.name AS class_name,
                u.name AS teacher_name
            FROM class_members cm
            JOIN classes c ON c.id = cm.class_id
            JOIN users u ON u.id = c.teacher_id
            WHERE cm.student_id = :studentId
              AND cm.status = 'ACTIVE'
              AND c.status = 'ACTIVE'
            ORDER BY cm.joined_at DESC
            """.trimIndent()

        val query = entityManager.createNativeQuery(sql)
        query.setParameter("studentId", studentId)

        @Suppress("UNCHECKED_CAST")
        val rows = query.resultList as List<Array<Any?>>

        return rows.map {
            StudentClassSummaryResponse(
                classId = (it[0] as Number).toLong(),
                name = it[1] as String,
                teacherName = it[2] as String,
            )
        }
    }

    fun findMyClassDetail(studentId: Long, classId: Long): StudentClassDetailResponse? {
        val classSql =
            """
            SELECT
                c.id AS class_id,
                c.name AS class_name
            FROM class_members cm
            JOIN classes c ON c.id = cm.class_id
            WHERE cm.student_id = :studentId
              AND cm.class_id = :classId
              AND cm.status = 'ACTIVE'
              AND c.status = 'ACTIVE'
            """.trimIndent()

        val classQuery = entityManager.createNativeQuery(classSql)
        classQuery.setParameter("studentId", studentId)
        classQuery.setParameter("classId", classId)

        val classRows = classQuery.resultList
        if (classRows.isEmpty()) return null

        val classRow = classRows[0] as Array<Any?>

        val setSql =
            """
            SELECT
                s.id AS set_id,
                s.title AS title,
                s.is_published AS is_published
            FROM sets s
            WHERE s.class_id = :classId
              AND s.status = 'ACTIVE'
              AND s.is_published = true
            ORDER BY s.created_at DESC
            """.trimIndent()

        val setQuery = entityManager.createNativeQuery(setSql)
        setQuery.setParameter("classId", classId)

        @Suppress("UNCHECKED_CAST")
        val setRows = setQuery.resultList as List<Array<Any?>>

        val sets = setRows.map {
            StudentClassSetResponse(
                setId = (it[0] as Number).toLong(),
                title = it[1] as String,
                isCompleted = false,
            )
        }

        return StudentClassDetailResponse(
            classId = (classRow[0] as Number).toLong(),
            name = classRow[1] as String,
            sets = sets,
        )
    }
}