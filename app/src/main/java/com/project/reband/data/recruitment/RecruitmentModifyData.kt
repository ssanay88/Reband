package com.project.reband.data.recruitment

/**
 * 공고 상태 변경
 * @param recruitment 공고 번호
 * @param state 공고 상태 [ACTIVE, INACTIVE]
 */
data class RecruitmentModifyData(
    val recruitment: Int,
    val state: String
)
