package com.sentencefit.application.test.dto

import com.fasterxml.jackson.databind.JsonNode
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SubmitStudentAnswerRequest(
    @field:NotNull(message = "questionId는 필수입니다.")
    val questionId: Long,

    @field:NotNull(message = "stepNo는 필수입니다.")
    val stepNo: Int,

    @field:NotBlank(message = "answerType은 필수입니다.")
    val answerType: String,

    @field:NotNull(message = "answer는 필수입니다.")
    val answer: JsonNode,
)