package com.project.reband.data.member

/**
 * 프로필 데이터
 * @param nickname 닉네임
 * @param instrument 악기 [GUITAR, DRUM, BASS, VOCAL, KEYBOARD]
 * @param experience 경력(단위 : 년)
 * @param firstDepth 도/특별시/광역시
 * @param secondDepth 시/군/구
 * @param gender 성별 [NONE, MAN, WOMAN]
 * @param chatUrl 오픈 카카오톡 URL
 * @param mediaUrl 연주 영상 URL
 * @param introduce 자기 소개
 */
data class ProfileRequestData(
    val nickname: String,
    val instrument: String,
    val experience: String,
    val firstDepth: String,
    val secondDepth: String,
    val gender: String,
    val chatUrl: String,
    val mediaUrl: String,
    val introduce: String
)
