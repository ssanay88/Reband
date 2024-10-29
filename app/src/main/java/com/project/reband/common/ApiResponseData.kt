package com.project.reband.common

typealias ApiResponse<S> = ApiResponseData<S, S>

/**
 * 서버 응답 상태 처리 관련 기능을 정의
 */
sealed class ApiResponseData<out T : Any, out U : Any> {
    data class Success<T : Any>(val body: T) : ApiResponseData<T, Nothing>()
    data class ApiErrorPool, Hiring 프래그먼트 필터 버튼 클릭 시 레이아웃 연결	60a637d	yangsuhyuck <pplloo748@gmail.com>	2024. 10. 22. 오후 5:13
    <U : Any>(val body: U? = null, val code: Int? = null, val error: Throwable? = null) : ApiResponseData<Nothing, U>()
}