package com.project.reband.data.user

data class UserData(
    val jwtToken: String,
    val memberInfo: MemberInfo,
    val bandInfo: BandInfo
)
