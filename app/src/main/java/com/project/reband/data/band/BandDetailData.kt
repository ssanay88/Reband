package com.project.reband.data.band

/**
 * 밴드 상세 정보
 *     val bandNo: Int,
 *     val bandName: String,
 *     val locationNo: Int,
 *     val introduce: String,
 */
data class BandDetailData(
    val code: String,
    val data: BandDetail,
    val message: String,
) {
    data class BandDetail(
        val bandNo: Int,
        val bandName: String,
        val locaationNo: Int,
        val introduce: String,
        val imageUrl: String,
        val mediaUrl: String,
        val bandState: String,
        val registerDate: String,
        val updateDate: String,
        val bandMemberList: MutableList<BandMemberData>
    )
}
