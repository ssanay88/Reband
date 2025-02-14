package com.project.reband.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProfileInfo(
    @SerialName("nickname") val nickname: String,
    @SerialName("instrument") val instrument: String,
    @SerialName("experience") val experience: Int,
    @SerialName("firstDepth") val firstDepth: String,
    @SerialName("secondDepth") val secondDepth: String,
    @SerialName("gender") val gender: String,
    @SerialName("age") val age: Int,
    @SerialName("chatUrl") val chatUrl: String = "",
    @SerialName("mediaUrl") val mediaUrl: String = "",
    @SerialName("introduce") val introduce: String = ""
)
