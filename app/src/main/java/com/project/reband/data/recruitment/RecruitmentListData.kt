package com.project.reband.data.recruitment

import com.project.reband.data.etc.HashTagData

sealed interface HiringData {

    object HireSortData : HiringData

    /**
     * 공고 목록 리스트
     */
    data class RecruitmentListData(
        val code: String,
        val data: RecruitmentList,
        val message: String
    )

    data class RecruitmentList(
        val recruitmentList: MutableList<Recruitment>
    ) : HiringData

    data class Recruitment(
        val recruitmentNo: Int,
        val bandNo: Int,
        val bandName: String,
        val content: String,
        val imageUrl: String,
        val hashTagList: List<String>
    ) : HiringData

}



