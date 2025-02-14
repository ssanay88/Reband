package com.project.reband.data.etc

/**
 * 공지사항 데이터
 * @param noticeNo 공지 번호
 * @param title 공지 제목
 * @param content 공지 내용
 */
data class NoticeData(
    val code: String,
    val data: NoticeList,
    val message: String
) {
    data class NoticeList(
        val noticeList: MutableList<Notice>
    ) {
        data class Notice(
            val noticeNo: Int,
            val title: String,
            val content: String
        )
    }
}


