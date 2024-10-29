package com.project.reband.data.band

/**
 * 밴드 구성 데이터
 * @param bandNo 밴드 번호
 * @param bandName 밴드 이름
 * @param locationNo 위치 번호
 * @param bandMemberInstrumentMap 밴드 구성원 번호
 * @param introduce 밴드 소개
 * @param hashTagList 밴드 특징
 * @param imageUrl 이미지 URL
 * @param mediaUrl 연주 영상 URL
 */
data class BandUpdateData(
    val bandNo: Int,
    val bandName: String,
    val locationNo: Int,
    val bandMemberInstrumentMap: MutableMap<String, Int>,
    val introduce: String,
    val hashTagList: MutableList<Int>,
    val imageUrl: String,
    val mediaUrl: String
)
