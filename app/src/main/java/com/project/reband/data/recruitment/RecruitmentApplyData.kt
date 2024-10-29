package com.project.reband.data.recruitment

/**
 * 공고 지원 상태 변경
 * @param recruitmentNo 공고 번호
 * @param memberNo 지원자 회원 번호
 * @param state 변경할 지원 상태 [WAIT, ACCEPT, REJECT]
 */
data class RecruitmentApplyData(
    val recruitmentNo: Int,
    val memberNo: Int,
    val state: String
)
