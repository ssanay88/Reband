package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.project.reband.GlobalApplication
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.etc.LocationInfo
import com.project.reband.network.intro.IntroRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class IntroViewModel: ViewModel() {

    private val mainRepository = IntroRepository()
    private val dataStore = GlobalApplication.getInstance().getDataStore()

    private val _locationList = MutableStateFlow<MutableList<LocationInfo>>(mutableListOf())

    private val _instrumentList = MutableStateFlow<MutableList<InstrumentData.InstrumentList.Instrument>?>(null)

    private val _hashTagList = MutableStateFlow<MutableList<HashTagData.HashTagList.HashTag>?>(null)

    private val _loadedAllData = MutableStateFlow<Boolean>(false)
    val loadedAllData = _loadedAllData.asStateFlow()

    suspend fun getLocationList() {
        runBlocking {
            val firstDepthLocationList = async {
                mainRepository.getDepth1Location(1)
            }.await()
            val locationList = mutableListOf<LocationInfo>()
            firstDepthLocationList.forEach { firstLocation ->
                mainRepository.getDepth2Location(2, firstLocation).collectLatest {
                    it.data.locationList.forEach { data ->
                        locationList.add(LocationInfo(firstLocation, data.locationName, data.locationNo))
                    }
                }
            }
            _locationList.emit(locationList)
            saveLocationList()
        }
    }

    suspend fun saveLocationList() {
        val locationListJson = Gson().toJson(_locationList.value)
        dataStore.setLocationList(locationListJson)
    }

    suspend fun getInstrumentList() {
        viewModelScope.launch {
            mainRepository.getInstrument().collectLatest { list ->
                _instrumentList.emit(list.instrumentList)
                saveInstrumentList()
            }
        }
    }

    suspend fun saveInstrumentList() {
        val instrumentListJson = Gson().toJson(_instrumentList.value)
        dataStore.setInstrumentList(instrumentListJson)
    }

    suspend fun getHashTagList() {
        viewModelScope.launch {
            mainRepository.getHashTag().collectLatest {
                _hashTagList.emit(it.hashTagList)
                saveHashTagList()
            }
        }
    }

    suspend fun saveHashTagList() {
        val hashTagListJson = Gson().toJson(_hashTagList.value)
        dataStore.setHashTagList(hashTagListJson)
    }

    suspend fun finishLoadData() {
        viewModelScope.launch {
            _loadedAllData.emit(true)
        }
    }

}