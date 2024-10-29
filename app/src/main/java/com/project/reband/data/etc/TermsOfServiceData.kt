package com.project.reband.data.etc

/**
 * 약관 조회
 * @param termsOfServiceNo 약관 번호
 * @param title 약관 제목
 * @param content 약관 내용
 */
data class TermsOfServiceData(
    val termsOfServiceNo: Int,
    val title: String,
    val content: String
)
