package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.utils.MutableEventFlow
import com.project.reband.utils.asEventFlow
import kotlinx.coroutines.launch

class PoolFragmentViewModel : ViewModel() {

    private val _btnClickHandler = MutableEventFlow<PoolSortBtnClickEvent>()
    val btnClickHandler = _btnClickHandler.asEventFlow()

    fun clickFilterBtn() = event(PoolSortBtnClickEvent.ClickFilterBtn)
    fun clickSortLatestBtn() = event(PoolSortBtnClickEvent.ClickSortLatestBtn)
    fun clickSortRegisterBtn() = event(PoolSortBtnClickEvent.ClickSortRegisterBtn)

    private fun event(event: PoolSortBtnClickEvent) {
        viewModelScope.launch {
            _btnClickHandler.emit(event)
        }
    }

}

sealed class PoolSortBtnClickEvent {
    data object ClickFilterBtn : PoolSortBtnClickEvent()

    data object ClickSortLatestBtn : PoolSortBtnClickEvent()

    data object ClickSortRegisterBtn : PoolSortBtnClickEvent()

}