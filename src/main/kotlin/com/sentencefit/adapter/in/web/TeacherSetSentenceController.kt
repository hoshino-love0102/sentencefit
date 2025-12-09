package com.sentencefit.adapter.`in`.web

import com.sentencefit.adapter.`in`.security.CustomUserPrincipal
import com.sentencefit.application.teachersentence.dto.CreateSetSentenceRequest
import com.sentencefit.application.teachersentence.dto.UpdateSentenceOrderRequest
import com.sentencefit.application.teachersentence.dto.UpdateSetSentenceRequest
import com.sentencefit.application.teachersentence.port.`in`.CreateSetSentenceUseCase
import com.sentencefit.application.teachersentence.port.`in`.DeleteSetSentenceUseCase
import com.sentencefit.application.teachersentence.port.`in`.GetSetSentenceUseCase
import com.sentencefit.application.teachersentence.port.`in`.UpdateSentenceOrderUseCase
import com.sentencefit.application.teachersentence.port.`in`.UpdateSetSentenceUseCase
import com.sentencefit.common.response.ApiResponse

import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher/classes/{classId}/sets/{setId}/sentences")
class TeacherSetSentenceController(
    private val createSetSentenceUseCase: CreateSetSentenceUseCase,
    private val getSetSentenceUseCase: GetSetSentenceUseCase,
    private val updateSetSentenceUseCase: UpdateSetSentenceUseCase,
    private val deleteSetSentenceUseCase: DeleteSetSentenceUseCase,
    private val updateSentenceOrderUseCase: UpdateSentenceOrderUseCase,
) {

    @PostMapping
    fun create(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
        @Valid @RequestBody request: CreateSetSentenceRequest,
    ) = ApiResponse.success(
        createSetSentenceUseCase.execute(principal.userId, classId, setId, request),
        "문장이 추가되었습니다."
    )

    @GetMapping
    fun getList(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int,
    ) = ApiResponse.success(
        getSetSentenceUseCase.getList(
            teacherId = principal.userId,
            classId = classId,
            setId = setId,
            keyword = keyword,
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "orderNo")),
        ),
        "문장 목록 조회 성공"
    )

    @GetMapping("/{sentenceId}")
    fun get(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
        @PathVariable sentenceId: Long,
    ) = ApiResponse.success(
        getSetSentenceUseCase.get(principal.userId, classId, setId, sentenceId),
        "문장 상세 조회 성공"
    )

    @PutMapping("/{sentenceId}")
    fun update(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
        @PathVariable sentenceId: Long,
        @Valid @RequestBody request: UpdateSetSentenceRequest,
    ) = ApiResponse.success(
        updateSetSentenceUseCase.execute(principal.userId, classId, setId, sentenceId, request),
        "문장이 수정되었습니다."
    )

    @DeleteMapping("/{sentenceId}")
    fun delete(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
        @PathVariable sentenceId: Long,
    ): ApiResponse<Map<String, Any>> {
        deleteSetSentenceUseCase.execute(principal.userId, classId, setId, sentenceId)

        return ApiResponse.success(
            mapOf(
                "id" to sentenceId,
                "status" to "DELETED",
            ),
            "문장이 삭제되었습니다."
        )
    }

    @PatchMapping("/order")
    fun updateOrder(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
        @Valid @RequestBody request: UpdateSentenceOrderRequest,
    ) = ApiResponse.success(
        data = run {
            updateSentenceOrderUseCase.execute(principal.userId, classId, setId, request)
            mapOf(
                "setId" to setId,
                "updatedCount" to request.items.size,
            )
        },
        message = "문장 순서가 변경되었습니다."
    )
}