package com.project.reband.data.recruitment

/**
 * 공고 목록 리스트
 */
data class RecruitmentList(
    val recruitmentList: MutableList<Recruitment>
)

/**
 * 공고 목록 데이터
 */
data class Recruitment(
    val recruitmentNo: Int,
    val bandNo: Int,
    val bandName: String,
    val instrumentNo: Int,
    val experience: Int,
    val gender: String,
    val ageGroup: Int,
    val content: String,
    val contractUrl: String,
    val registerData: String,
    val updateDate: String
)
