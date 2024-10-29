package com.project.reband.data.band

/**
 * 밴드 악기별 멤버 목록 조회
 */
data class BandInstrumentMemberList(
    val bandInstrumentMemberList: MutableList<BandInstrumentMember>
)

data class BandInstrumentMember(
    val bandMemberNo: Int,
    val memberNo: Int,
    val memberType: String,
    val instrumentNo: Int,
    val memberGrade: String
)