package com.project.reband.data.etc

/**
 * 악기 데이터
 * @param instrumentNo 악기 번호
 * @param name 악기 이름
 */
data class InstrumentData(
    val code: String,
    val data: InstrumentList,
    val message: String
) {
    data class InstrumentList(
        val instrumentList: MutableList<Instrument>
    ) {
        data class Instrument(
            val instrumentNo: Int,
            val name: String
        )
    }
}
