package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.utils.MutableEventFlow
import com.project.reband.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MybandMemberFragmentViewModel @Inject constructor() : ViewModel() {

    private val _btnClickHandler = MutableEventFlow<MybandMemberBtnClickEvent>()
    val btnClickHandler = _btnClickHandler.asEventFlow()

    fun clickBackBtn() = event(MybandMemberBtnClickEvent.ClickBackBtn)
    fun clickBandInfoInquiryBtn() = event(MybandMemberBtnClickEvent.ClickBandInfoInquiryBtn)
    fun clickBandWithdrawalBtn() = event(MybandMemberBtnClickEvent.ClickBandWithdrawalBtn)

    private fun event(event: MybandMemberBtnClickEvent) {
        viewModelScope.launch {
            _btnClickHandler.emit(event)
        }
    }
}

sealed class MybandMemberBtnClickEvent {
    data object ClickBackBtn : MybandMemberBtnClickEvent()

    data object ClickBandInfoInquiryBtn : MybandMemberBtnClickEvent()

    data object ClickBandWithdrawalBtn : MybandMemberBtnClickEvent()

}