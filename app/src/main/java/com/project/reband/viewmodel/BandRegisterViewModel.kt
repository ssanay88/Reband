package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.band.BandCreateData
import com.project.reband.network.band.BandRepository
import kotlinx.coroutines.launch

class BandRegisterViewModel: ViewModel() {
    private val repository = BandRepository()

    fun createBand(bandCreateData: BandCreateData) {
        viewModelScope.launch {
            repository.createBand(bandCreateData)
        }
    }


}