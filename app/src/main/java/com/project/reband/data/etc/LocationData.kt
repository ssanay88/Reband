package com.project.reband.data.etc

/**
 * 지역 조회
 * @param locationNo 지역 번호
 * @param locationName 지역 이름 (서울 특별시 or 관악구)
 */
data class LocationData(
    val locationNo: Int,
    val locationName: String
)
