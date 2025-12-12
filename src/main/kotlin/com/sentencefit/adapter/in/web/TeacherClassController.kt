package com.sentencefit.adapter.`in`.web

import com.sentencefit.adapter.`in`.security.CustomUserPrincipal
import com.sentencefit.application.classjoin.port.`in`.CreateJoinCodeUseCase
import com.sentencefit.application.classjoin.port.`in`.GetClassMembersUseCase
import com.sentencefit.application.classjoin.port.`in`.GetJoinCodeUseCase
import com.sentencefit.application.classjoin.port.`in`.RegenerateJoinCodeUseCase
import com.sentencefit.application.classjoin.port.`in`.RemoveClassMemberUseCase
import com.sentencefit.application.teacherclass.dto.CreateClassRequest
import com.sentencefit.application.teacherclass.dto.UpdateClassRequest
import com.sentencefit.application.teacherclass.port.`in`.CreateClassUseCase
import com.sentencefit.application.teacherclass.port.`in`.DeleteClassUseCase
import com.sentencefit.application.teacherclass.port.`in`.GetClassUseCase
import com.sentencefit.application.teacherclass.port.`in`.UpdateClassUseCase
import com.sentencefit.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher/classes")
class TeacherClassController(
    private val createClassUseCase: CreateClassUseCase,
    private val getClassUseCase: GetClassUseCase,
    private val updateClassUseCase: UpdateClassUseCase,
    private val deleteClassUseCase: DeleteClassUseCase,
    private val createJoinCodeUseCase: CreateJoinCodeUseCase,
    private val getJoinCodeUseCase: GetJoinCodeUseCase,
    private val regenerateJoinCodeUseCase: RegenerateJoinCodeUseCase,
    private val getClassMembersUseCase: GetClassMembersUseCase,
    private val removeClassMemberUseCase: RemoveClassMemberUseCase,
) {

    @PostMapping
    fun create(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @Valid @RequestBody request: CreateClassRequest,
    ) = ApiResponse.success(
        createClassUseCase.execute(principal.userId, request),
        "클래스가 생성되었습니다."
    )

    @GetMapping
    fun getList(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
    ) = ApiResponse.success(
        getClassUseCase.getList(
            teacherId = principal.userId,
            keyword = keyword,
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")),
        ),
        "클래스 목록 조회 성공"
    )

    @GetMapping("/{classId}")
    fun get(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
    ) = ApiResponse.success(
        getClassUseCase.get(principal.userId, classId),
        "클래스 상세 조회 성공"
    )

    @PutMapping("/{classId}")
    fun update(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @Valid @RequestBody request: UpdateClassRequest,
    ) = ApiResponse.success(
        updateClassUseCase.execute(principal.userId, classId, request),
        "클래스가 수정되었습니다."
    )

    @DeleteMapping("/{classId}")
    fun delete(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
    ): ApiResponse<Map<String, Any>> {
        deleteClassUseCase.execute(principal.userId, classId)

        return ApiResponse.success(
            mapOf(
                "id" to classId,
                "status" to "DELETED",
            ),
            "클래스가 삭제되었습니다."
        )
    }

    @PostMapping("/{classId}/join-code")
    fun createJoinCode(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
    ) = ApiResponse.success(
        createJoinCodeUseCase.execute(principal.userId, classId),
        "참여 코드 생성 성공"
    )

    @GetMapping("/{classId}/join-code")
    fun getJoinCode(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
    ) = ApiResponse.success(
        getJoinCodeUseCase.execute(principal.userId, classId),
        "참여 코드 조회 성공"
    )

    @PutMapping("/{classId}/join-code")
    fun regenerateJoinCode(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
    ) = ApiResponse.success(
        regenerateJoinCodeUseCase.execute(principal.userId, classId),
        "참여 코드 재생성 성공"
    )

    @GetMapping("/{classId}/members")
    fun getMembers(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
    ) = ApiResponse.success(
        getClassMembersUseCase.execute(
            teacherId = principal.userId,
            classId = classId,
            keyword = keyword,
            pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "joinedAt"),
            ),
        ),
        "클래스 멤버 목록 조회 성공"
    )

    @DeleteMapping("/{classId}/members/{studentId}")
    fun removeMember(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable studentId: Long,
    ): ApiResponse<Map<String, Any>> {
        removeClassMemberUseCase.execute(
            teacherId = principal.userId,
            classId = classId,
            studentId = studentId,
        )

        return ApiResponse.success(
            mapOf(
                "id" to studentId,
                "status" to "REMOVED",
            ),
            "클래스 멤버 제거 성공"
        )
    }
}