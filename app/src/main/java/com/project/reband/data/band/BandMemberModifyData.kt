package com.project.reband.data.band

/**
 * 밴드 멤버 상태 변경
 * @param bandNo 밴드 번호
 * @param modifyMemberNo 상태 변경 될 회원 번호
 * @param memberStatus 상태 변경 될 회원 상태 [ACTIVE, INACTIVE, SUSPENDED, LEADER_DELEGATE]
 */
data class BandMemberModifyData(
    val bandNo: Int,
    val modifyMemberNo: Int,
    val memberStatus: String
)
