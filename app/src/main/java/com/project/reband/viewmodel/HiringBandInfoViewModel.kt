package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.band.BandDetailData
import com.project.reband.network.band.BandRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiringBandInfoViewModel @Inject constructor(
    private val bandRepository: BandRepository
) : ViewModel() {

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