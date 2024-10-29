package com.project.reband.data.band

/**
 * 신규 밴드 생성
 * @param instrumentNo 밴드 포지션 번호
 * @param experience 경력 (단위 : 년)
 * @param gender 성별 [NONE, MAN, WOMAN]
 * @param ageGroup 연령
 * @param content 상세 내용
 * @param contractUrl 오픈카톡 URL
 * @param hashTagList 밴드 특징
 */
data class BandCreateData(
    val instrumentNo: Int,
    val experience: Int,
    val gender: String,
    val ageGroup: Int,
    val content: String,
    val contractUrl: String,
    val hashTagList: MutableList<Int>
)
