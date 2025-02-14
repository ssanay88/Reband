package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.talentpool.TalentPoolDetailData
import com.project.reband.network.talentpool.TalentPoolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PoolDetailFragmentViewModel : ViewModel() {

    private val talentPoolRepository = TalentPoolRepository()

    private val _poolDetail = MutableStateFlow<TalentPoolDetailData.TalentPoolDetail?>(null)
    val poolDetail = _poolDetail.asStateFlow()

    fun getTalentPoolDetail(poolNo: Int) {
        viewModelScope.launch {
            talentPoolRepository.getTalentPoolDetail(poolNo).collectLatest {
                _poolDetail.emit(it)
                Log.d("tngur", "talentPooil : ${it}")
            }
        }
    }

    fun suggestJoinToBand(poolNo: Int) {
        viewModelScope.launch {
            talentPoolRepository.suggestJoinToBand(poolNo)
        }
    }

}