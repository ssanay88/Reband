package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import com.project.reband.data.etc.NotificationData
import com.project.reband.network.etc.EtcRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationFragmentViewModel : ViewModel() {
    private val repository = EtcRepository()

    private val _notificationList = MutableStateFlow<List<NotificationData.Notification>?>(null)
    val notificationList = _notificationList.asStateFlow()

    suspend fun getNotification() {
        repository.getNotification().collect {
            _notificationList.value = it.data
        }
    }


}