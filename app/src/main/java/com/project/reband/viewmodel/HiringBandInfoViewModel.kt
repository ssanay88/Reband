package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.band.BandDetailData
import com.project.reband.network.band.BandRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HiringBandInfoViewModel : ViewModel() {
    private val bandRepository = BandRepository()

    private val _bandDetail = MutableStateFlow<BandDetailData.BandDetail?>(null)
    val bandDetail = _bandDetail.asStateFlow()

    fun getBandDetail(bandNo: Int) {
        viewModelScope.launch {
            bandRepository.getBandDetail(bandNo).collectLatest {
                _bandDetail.emit(it.data)
            }
        }
    }



}