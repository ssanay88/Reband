package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.recruitment.HiringData
import com.project.reband.network.recruitment.RecruitmentRepository
import com.project.reband.utils.MutableEventFlow
import com.project.reband.utils.asEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HiringFragmentViewModel : ViewModel() {
    private val recruitmentRepository = RecruitmentRepository()

    private val _recruitmentList = MutableStateFlow<List<HiringData.Recruitment>?>(null)
    val recruitmentList = _recruitmentList.asStateFlow()

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

    fun getRecruitmentList() {
        viewModelScope.launch {
            recruitmentRepository.getRecruitmentList().collectLatest {
                _recruitmentList.emit(it.recruitmentList)
            }
        }
    }
}

sealed class HireSortBtnClickEvent {
    data object ClickFilterBtn : HireSortBtnClickEvent()

    data object ClickSortLatestBtn : HireSortBtnClickEvent()

    data object ClickSortRegisterBtn : HireSortBtnClickEvent()

}