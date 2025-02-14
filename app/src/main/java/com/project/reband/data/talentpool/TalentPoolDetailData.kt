package com.project.reband.data.talentpool

import com.project.reband.data.etc.HashTagData

/**
 * 인재풀 상세 조회
 */
data class TalentPoolDetailData(
    val code: String,
    val data: TalentPoolDetail,
    val message: String
) {
    data class TalentPoolDetail(
        val no: Int,
        val memberNo: Int,
        val location: String,
        val instrument: String,
        val experience: Int,
        val days: MutableList<String>,
        val hashTags: MutableList<HashTagData.HashTagList.HashTag>,
        val mediaUrl: String,
        val introduce: String,
        val chatUrl: String
    )
}
