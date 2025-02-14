package com.project.reband.data.etc

data class LocationFirstDepthData(
    val code: String,
    val data: LocationFirstDepth,
    val message: String
)

data class LocationFirstDepth(
    val firstDepthLocationName : MutableList<String>
)

data class LocationSecondDepthData(
    val code: String,
    val data: LocationSecondDepth,
    val message: String
)

data class LocationSecondDepth(
    val locationList: MutableList<Location>
)

data class Location(
    val locationNo: Int,
    val locationName: String
)


