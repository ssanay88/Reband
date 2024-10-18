package com.example.reband.data

data class RecruitingBandData(
    val bandName: String,
    val bandImage: String,
    val targetPosition: String,
    val hashTag: String,
    val description: String = ""
)