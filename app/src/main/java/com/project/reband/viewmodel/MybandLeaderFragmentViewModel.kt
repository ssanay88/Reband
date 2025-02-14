package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.band.BandDetailData
import com.project.reband.network.band.BandRepository
import com.project.reband.utils.MutableEventFlow
import com.project.reband.utils.asEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MybandLeaderFragmentViewModel: ViewModel() {

    private val repository = BandRepository()

    private val _bandInfo = MutableStateFlow<BandDetailData.BandDetail?>(null)
    val bandInfo = _bandInfo.asStateFlow()

    private val _btnClickHandler = MutableEventFlow<MybandLeaderBtnClickEvent>()
    val btnClickHandler = _btnClickHandler.asEventFlow()

    fun clickBackBtn() = event(MybandLeaderBtnClickEvent.ClickBackBtn)
    fun clickBandInfoInquiryBtn() = event(MybandLeaderBtnClickEvent.ClickBandInfoInquiryBtn)
    fun clickBandInfoFixBtn() = event(MybandLeaderBtnClickEvent.ClickBandInfoFixBtn)
    fun clickBandMemberManageBtn() = event(MybandLeaderBtnClickEvent.ClickBandMemberManageBtn)
    fun clickChangeBandLeaderBtn() = event(MybandLeaderBtnClickEvent.ClickChangeBandLeaderBtn)
    fun clickBandWithdrawalBtn() = event(MybandLeaderBtnClickEvent.ClickBandWithdrawalBtn)
    fun clickBandBreakupBtn() = event(MybandLeaderBtnClickEvent.ClickBandBreakupBtn)

    private fun event(event: MybandLeaderBtnClickEvent) {
        viewModelScope.launch {
            _btnClickHandler.emit(event)
        }
    }

    fun getBandDetail(bandNo: Int) {
        viewModelScope.launch {
            repository.getBandDetail(bandNo).collectLatest {
                _bandInfo.emit(it.data)
            }
        }
    }

}

sealed class MybandLeaderBtnClickEvent {
    data object ClickBackBtn : MybandLeaderBtnClickEvent()

    data object ClickBandInfoInquiryBtn : MybandLeaderBtnClickEvent()

    data object ClickBandInfoFixBtn : MybandLeaderBtnClickEvent()

    data object ClickBandMemberManageBtn : MybandLeaderBtnClickEvent()

    data object ClickChangeBandLeaderBtn : MybandLeaderBtnClickEvent()

    data object ClickBandWithdrawalBtn : MybandLeaderBtnClickEvent()

    data object ClickBandBreakupBtn : MybandLeaderBtnClickEvent()
}