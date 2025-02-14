package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.etc.LocationFirstDepth
import com.project.reband.data.etc.LocationSecondDepth
import com.project.reband.network.main.MainRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    private val _instrumentList = MutableStateFlow<InstrumentData.InstrumentList?>(null)
    val instrumentList = _instrumentList.asStateFlow()

    private val _hashTagList = MutableStateFlow<HashTagData.HashTagList?>(null)
    val hashTagList = _hashTagList.asStateFlow()

    suspend fun getInstrumentList() {
        viewModelScope.launch {
            mainRepository.getInstrument().collectLatest {
                _instrumentList.emit(it)
            }

        }
    }

    suspend fun getHashTagList() {
        viewModelScope.launch {
            mainRepository.getHashTag().collectLatest {
                _hashTagList.emit(it)
            }
        }
    }

}