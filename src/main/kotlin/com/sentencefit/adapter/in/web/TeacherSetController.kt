package com.sentencefit.adapter.`in`.web

import com.sentencefit.adapter.`in`.security.CustomUserPrincipal
import com.sentencefit.application.teacherset.dto.CreateSetRequest
import com.sentencefit.application.teacherset.dto.UpdateSetRequest
import com.sentencefit.application.teacherset.port.`in`.CreateSetUseCase
import com.sentencefit.application.teacherset.port.`in`.DeleteSetUseCase
import com.sentencefit.application.teacherset.port.`in`.GetSetUseCase
import com.sentencefit.application.teacherset.port.`in`.UpdateSetUseCase
import com.sentencefit.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher/classes/{classId}/sets")
class TeacherSetController(
    private val createSetUseCase: CreateSetUseCase,
    private val getSetUseCase: GetSetUseCase,
    private val updateSetUseCase: UpdateSetUseCase,
    private val deleteSetUseCase: DeleteSetUseCase,
) {

    @PostMapping
    fun create(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @Valid @RequestBody request: CreateSetRequest,
    ) = ApiResponse.success(
        createSetUseCase.execute(principal.userId, classId, request),
        "세트가 생성되었습니다."
    )

    @GetMapping
    fun getList(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) isPublished: Boolean?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
    ) = ApiResponse.success(
        getSetUseCase.getList(
            teacherId = principal.userId,
            classId = classId,
            keyword = keyword,
            isPublished = isPublished,
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")),
        ),
        "세트 목록 조회 성공"
    )

    @GetMapping("/{setId}")
    fun get(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
    ) = ApiResponse.success(
        getSetUseCase.get(principal.userId, classId, setId),
        "세트 상세 조회 성공"
    )

    @PutMapping("/{setId}")
    fun update(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
        @Valid @RequestBody request: UpdateSetRequest,
    ) = ApiResponse.success(
        updateSetUseCase.execute(principal.userId, classId, setId, request),
        "세트가 수정되었습니다."
    )

    @DeleteMapping("/{setId}")
    fun delete(
        @AuthenticationPrincipal principal: CustomUserPrincipal,
        @PathVariable classId: Long,
        @PathVariable setId: Long,
    ): ApiResponse<Map<String, Any>> {
        deleteSetUseCase.execute(principal.userId, classId, setId)

        return ApiResponse.success(
            mapOf(
                "id" to setId,
                "status" to "DELETED",
            ),
            "세트가 삭제되었습니다."
        )
    }
}