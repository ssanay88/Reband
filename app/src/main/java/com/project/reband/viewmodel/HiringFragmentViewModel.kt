package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.utils.MutableEventFlow
import com.project.reband.utils.asEventFlow
import kotlinx.coroutines.launch

class HiringFragmentViewModel : ViewModel() {

    private val _btnClickHandler = MutableEventFlow<HireSortBtnClickEvent>()
    val btnClickHandler = _btnClickHandler.asEventFlow()

    fun clickFilterBtn() = event(HireSortBtnClickEvent.ClickFilterBtn)
    fun clickSortLatestBtn() = event(HireSortBtnClickEvent.ClickSortLatestBtn)
    fun clickSortRegisterBtn() = event(HireSortBtnClickEvent.ClickSortRegisterBtn)

    private fun event(event: HireSortBtnClickEvent) {
        viewModelScope.launch {
            _btnClickHandler.emit(event)
        }
    }

}

sealed class HireSortBtnClickEvent {
    data object ClickFilterBtn : HireSortBtnClickEvent()

    data object ClickSortLatestBtn : HireSortBtnClickEvent()

    data object ClickSortRegisterBtn : HireSortBtnClickEvent()

}