package com.project.reband.data.member

/**
 * 프로필 조회
 */
data class ProfileData(
    val nickname: String,
    val location: String,
    val instrument: String,
    val experience: String,
    val gender: String,
    val introduce: String,
    val chatUrl: String,
    val mediaUrl: String
)
