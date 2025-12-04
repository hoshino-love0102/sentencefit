package com.sentencefit.common.exception

import com.sentencefit.common.response.ApiResponse
import com.sentencefit.domain.auth.exception.AuthException
import com.sentencefit.domain.user.exception.UserException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AuthException::class)
    fun handleAuthException(e: AuthException): ResponseEntity<ApiResponse<Void>> =
        ResponseEntity
            .status(e.errorCode.status)
            .body(ApiResponse.fail(e.message ?: e.errorCode.message))

    @ExceptionHandler(UserException::class)
    fun handleUserException(e: UserException): ResponseEntity<ApiResponse<Void>> =
        ResponseEntity
            .status(e.errorCode.status)
            .body(ApiResponse.fail(e.message ?: e.errorCode.message))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Void>> {
        val message = e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "잘못된 요청입니다."
        return ResponseEntity.badRequest().body(ApiResponse.fail(message))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ApiResponse<Void>> =
        ResponseEntity.badRequest().body(ApiResponse.fail(e.message ?: "잘못된 요청입니다."))

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Void>> =
        ResponseEntity.internalServerError().body(ApiResponse.fail("서버 내부 오류"))
}