package com.project.reband.data.band

/**
 * 밴드 상태 변경
 * @param bandNo 밴드 번호
 * @param bandStatus 밴드 상태
 */
data class BandModifyData(
    val bandNo: Int,
    val bandStatus: String
)
