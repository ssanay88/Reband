package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.band.BandDetailData
import com.project.reband.data.band.BandMemberData
import com.project.reband.data.band.BandMemberModifyData
import com.project.reband.network.band.BandRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyBandInfoFragmentViewModel: ViewModel() {

    private val repository =  BandRepository()

    private val _bandInfo = MutableStateFlow<BandDetailData.BandDetail?>(null)
    val bandInfo = _bandInfo.asStateFlow()

    private val _showDialog = MutableStateFlow<ShowDialog?>(null)
    val showDialog = _showDialog.asStateFlow()

    fun getBandInfo(bandNo: Int) {
        viewModelScope.launch {
            repository.getBandDetail(bandNo).collect {
                _bandInfo.emit(it.data)
            }
        }
    }

    fun showDialog(dialog: ShowDialog) {
        viewModelScope.launch {
            _showDialog.emit(dialog)
        }
    }

    fun showMemberInfoDialog(memberData: BandMemberData) {
        showDialog(ShowDialog.MemberInfoDialog(memberData))
    }

    fun showDeportDialog(name: String, bandNo: Int, memberNo: Int) {
        showDialog(ShowDialog.DeportDialog(name, bandNo, memberNo))
    }

    fun showDeportCompleteDialog() {
        showDialog(ShowDialog.DeportCompleteDialog)
    }

    fun deportMember(bandNo: Int, modifyMemberNo: Int) {
        viewModelScope.launch {
            repository.modifyMemberStatus(
                BandMemberModifyData(
                    bandNo,
                    modifyMemberNo,
                    "SUSPENDED"
                )
            )
        }
    }

}

sealed interface ShowDialog {

    data class MemberInfoDialog(
        val memberInfo: BandMemberData
    ): ShowDialog

    data class DeportDialog(
        val name: String,
        val bandNo: Int,
        val memberNo: Int
    ): ShowDialog

    object DeportCompleteDialog: ShowDialog
}