package com.project.reband.data.etc

/**
 * 해시 태그 조회
 * @param hashTagNo 해시태그 번호
 * @param name 해시태그
 */
data class HashTagData(
    val code: String,
    val data: HashTagList,
    val message: String
) {
    data class HashTagList(
        val hashTagList: MutableList<HashTag>
    ) {
        data class HashTag(
            val hashTagNo: Int,
            val name: String
        )
    }
}
