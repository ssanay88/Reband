package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.talentpool.PoolData
import com.project.reband.network.talentpool.TalentPoolRepository
import com.project.reband.utils.MutableEventFlow
import com.project.reband.utils.asEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PoolFragmentViewModel : ViewModel() {
    private val talentPoolRepository = TalentPoolRepository()

    private val _talentPool = MutableStateFlow<List<PoolData.TalentPool>>(emptyList())
    val talentPool = _talentPool.asStateFlow()

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

    fun getTalentPool() {
        viewModelScope.launch {
            talentPoolRepository.getTalentPool().collectLatest {
                _talentPool.emit(it.data)
            }
        }
    }

}

sealed class PoolSortBtnClickEvent {
    data object ClickFilterBtn : PoolSortBtnClickEvent()

    data object ClickSortLatestBtn : PoolSortBtnClickEvent()

    data object ClickSortRegisterBtn : PoolSortBtnClickEvent()

}