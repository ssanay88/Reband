package com.project.reband.data

data class FindBandData(
    val id: Int,
    val myPosition: String,
    val positionImage: String,
    val career: Int,
    val hashTag: MutableList<String>,
    val introduce: String
)
