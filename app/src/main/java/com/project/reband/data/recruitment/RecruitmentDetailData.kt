package com.project.reband.data.recruitment

data class RecruitmentDetailData(
    val code: String,
    val data: RecruitmentDetail,
    val message: String
) {
    data class RecruitmentDetail(
        val recruitmentNo: Int,
        val bandNo: Int,
        val bandName: String,
        val instrumentNo: Int,
        val minExperience: Int,
        val maxExperience: Int,
        val gender: String,
        val ageGroup: Int,
        val content: String,
        val contractUrl: String,
        val registerData: String,
        val updateDate: String,
        val location: String,
        val imageUrl: String
    )
}

