package com.project.reband.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.band.BandCreateData
import com.project.reband.network.band.BandRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BandRegisterViewModel @Inject constructor(
    private val repository: BandRepository
): ViewModel() {

    fun createBand(bandCreateData: BandCreateData) {
        viewModelScope.launch {
            repository.createBand(bandCreateData)
        }
    }


}