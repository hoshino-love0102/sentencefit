package com.sentencefit.common.exception

import com.sentencefit.common.response.ApiResponse
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ApiResponse<Void>> {
        log.warn("Business exception: {}", e.message)
        return ResponseEntity
            .status(e.errorCode.status)
            .body(ApiResponse.fail(e.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
    ): ResponseEntity<ApiResponse<Void>> {
        val fieldError = e.bindingResult.fieldErrors.firstOrNull()
        val message = fieldError?.defaultMessage ?: "잘못된 요청입니다."

        log.warn("Validation failed: {}", fieldError?.let { "${it.field}=${it.rejectedValue}" })

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.fail(message))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        e: ConstraintViolationException,
    ): ResponseEntity<ApiResponse<Void>> {
        val message = e.constraintViolations.firstOrNull()?.message ?: "잘못된 요청입니다."

        log.warn("Constraint violation: {}", message)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.fail(message))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        e: MethodArgumentTypeMismatchException,
    ): ResponseEntity<ApiResponse<Void>> {
        val message = "요청 파라미터 형식이 올바르지 않습니다."

        log.warn("Type mismatch: parameter={}, value={}", e.name, e.value)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.fail(message))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(
        e: MissingServletRequestParameterException,
    ): ResponseEntity<ApiResponse<Void>> {
        val message = "필수 요청 파라미터가 누락되었습니다: ${e.parameterName}"

        log.warn("Missing request parameter: {}", e.parameterName)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.fail(message))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        e: HttpMessageNotReadableException,
    ): ResponseEntity<ApiResponse<Void>> {
        log.warn("Invalid request body", e)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.fail("요청 본문 형식이 올바르지 않습니다."))
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        e: HttpRequestMethodNotSupportedException,
    ): ResponseEntity<ApiResponse<Void>> {
        log.warn("Method not allowed: {}", e.method)

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ApiResponse.fail("지원하지 않는 HTTP 메서드입니다."))
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(
        e: NoHandlerFoundException,
    ): ResponseEntity<ApiResponse<Void>> {
        log.warn("No handler found: {} {}", e.httpMethod, e.requestURL)

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.fail("요청한 경로를 찾을 수 없습니다."))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Void>> {
        log.error("Unhandled exception occurred", e)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.fail("서버 내부 오류가 발생했습니다."))
    }
}