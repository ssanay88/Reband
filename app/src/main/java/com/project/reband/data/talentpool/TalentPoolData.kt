package com.project.reband.data.talentpool

import com.project.reband.data.etc.HashTagData

/**
 * 인재풀 정보
 * @param no 인재풀 번호
 * @param title 악기
 * @param content 자기 소개
 * @param experience 경력
 * @param location 위치
 * @param hashTagList 해시태그
 */
data class TalentPoolData(
    val no: Int,
    val title: String,
    val content: String,
    val experience: Int,
    val location: String,
    val hashTagList: List<HashTagData>
)