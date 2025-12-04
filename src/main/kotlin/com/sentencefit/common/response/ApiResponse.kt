package com.sentencefit.common.response

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String
) {
    companion object {
        fun <T> success(data: T?, message: String = "OK"): ApiResponse<T> =
            ApiResponse(success = true, data = data, message = message)

        fun fail(message: String): ApiResponse<Void> =
            ApiResponse(success = false, data = null, message = message)
    }
}