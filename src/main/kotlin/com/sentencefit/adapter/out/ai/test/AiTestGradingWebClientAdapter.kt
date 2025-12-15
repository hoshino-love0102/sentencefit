package com.sentencefit.adapter.out.ai.test

import com.sentencefit.application.test.port.out.AiTestGradingCommand
import com.sentencefit.application.test.port.out.AiTestGradingPort
import com.sentencefit.application.test.port.out.AiTestGradingResult
import com.sentencefit.domain.test.exception.StudentTestErrorCode
import com.sentencefit.domain.test.exception.StudentTestException
import com.sentencefit.domain.test.model.ErrorType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

@Component
class AiTestGradingWebClientAdapter(
    @Qualifier("aiWebClient")
    private val aiWebClient: WebClient,
) : AiTestGradingPort {

    override fun gradeAll(commands: List<AiTestGradingCommand>): List<AiTestGradingResult> {
        if (commands.isEmpty()) return emptyList()

        val request = AiGradingBatchRequest(
            items = commands.map { command ->
                AiGradingItemRequest(
                    questionId = command.questionId,
                    stepNo = command.stepNo,
                    stepType = command.stepType.name,
                    answerType = command.answerType.name,
                    englishText = command.englishText,
                    koreanText = command.koreanText,
                    grammarPoint = command.grammarPoint,
                    correctAnswer = command.correctAnswer,
                    studentAnswer = buildStudentAnswer(command),
                )
            }
        )

        val response = try {
            aiWebClient.post()
                .uri("/v1/grading/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AiGradingBatchResponse::class.java)
                .block()
                ?: throw StudentTestException(
                    StudentTestErrorCode.AI_GRADING_FAILED,
                    "AI 서버 응답이 비어 있습니다."
                )
        } catch (e: WebClientResponseException) {
            throw StudentTestException(
                StudentTestErrorCode.AI_GRADING_FAILED,
                "AI 서버 호출 실패: ${e.statusCode.value()} ${e.responseBodyAsString}"
            )
        } catch (e: Exception) {
            throw StudentTestException(
                StudentTestErrorCode.AI_GRADING_FAILED,
                "AI 채점 중 오류가 발생했습니다: ${e.message}"
            )
        }

        return response.results.map { result ->
            AiTestGradingResult(
                questionId = result.questionId,
                stepNo = result.stepNo,
                isCorrect = result.isCorrect,
                correctAnswer = result.correctAnswer,
                explanation = result.explanation,
                errorType = result.errorType?.let(::toErrorType),
                usedAi = result.usedAi,
                canRetest = result.canRetest,
            )
        }
    }

    private fun buildStudentAnswer(command: AiTestGradingCommand): Any {
        return command.submittedAnswerTokens ?: command.submittedAnswerText.orEmpty()
    }

    private fun toErrorType(value: String): ErrorType {
        return try {
            ErrorType.valueOf(value)
        } catch (_: IllegalArgumentException) {
            ErrorType.UNKNOWN
        }
    }
}