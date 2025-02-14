package com.project.reband.data.etc

/**
 * 알림 조회
 */

data class NotificationData(
    val code: String,
    val message: String,
    val data: List<Notification>
) {
    data class Notification(
        val title: String,
        val content: String,
        val redirectTarget: String,
        val registerDate: String
    )
}

