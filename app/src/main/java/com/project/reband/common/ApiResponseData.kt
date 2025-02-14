package com.project.reband.common

typealias ApiResponse<S> = ApiResponseData<S, S>

/**
 * 서버 응답 상태 처리 관련 기능을 정의
 */
sealed class ApiResponseData<out T : Any, out U : Any> {
    data class Success<T : Any>(val body: T) : ApiResponseData<T, Nothing>()
    data class ApiError<U : Any>(val body: U? = null, val code: Int? = null, val error: Throwable? = null) : ApiResponseData<Nothing, U>()
}