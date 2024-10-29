package com.project.reband.data.talentpool

/**
 * 밴드 가입 제안
 * @param no 가입 제안 번호
 * @param bandNo 밴드 번호
 * @param bandName 밴드 이름
 * @param state 현재 상태 [ OFFER, CANCEL, APPROVE, REJECT ]
 * @param registerDate 등록일
 * @param updateDate 업데이트일
 */
data class TalentPoolOfferData(
    val no: Int,
    val bandNo: Int,
    val bandName: Int,
    val state: String,
    val registerDate: String,
    val updateDate: String
)
