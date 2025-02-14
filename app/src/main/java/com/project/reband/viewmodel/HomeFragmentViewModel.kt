package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.reband.GlobalApplication
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.recruitment.HiringData
import com.project.reband.event.HomeClickEvent
import com.project.reband.network.band.BandRepository
import com.project.reband.network.recruitment.RecruitmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    private val recruitmentRepository = RecruitmentRepository()

    private val _recruitmentList = MutableStateFlow<MutableList<HiringData.Recruitment>?>(null)
    val recruitmentList = _recruitmentList.asStateFlow()

    private val _clickEvent = MutableStateFlow<HomeClickEvent?>(null)
    val clickEvent = _clickEvent.asStateFlow()

    private val dataStore = GlobalApplication.getInstance().getDataStore()
    var hashTagList : MutableList<HashTagData.HashTagList.HashTag> = mutableListOf()
    var instrumentList : MutableList<InstrumentData.InstrumentList.Instrument> = mutableListOf()


    fun getHashTagList() {
        viewModelScope.launch {
            dataStore.hashTagList.collectLatest {
                if (it.isNotEmpty()) {
                    hashTagList = Gson().fromJson(dataStore.hashTagList.first(), object : TypeToken<MutableList<HashTagData.HashTagList.HashTag?>?>() {}.type)
                }
            }

        }
    }

    fun getInstrumentList() {
        viewModelScope.launch {
            dataStore.instrumentList.collectLatest {
                if (it.isNotEmpty()) {
                    instrumentList = Gson().fromJson(dataStore.instrumentList.first(), object : TypeToken<MutableList<InstrumentData.InstrumentList.Instrument?>?>() {}.type)
                }
            }
        }
    }

    fun emitEvent(event: HomeClickEvent) {
        viewModelScope.launch {
            _clickEvent.emit(event)
        }
    }

    fun getRecruitmentListData() {
        viewModelScope.launch {
            recruitmentRepository.getRecruitmentList().collectLatest {
                _recruitmentList.emit(it.recruitmentList)
            }
        }
    }

    fun clickSearchNewMemberAddBtn() = emitEvent(HomeClickEvent.SearchNewMemberAddBtnClick)

    fun clickShowMoreRecruitingBandBtn() = emitEvent(HomeClickEvent.ShowMoreRecruitingBandBtnClick)

    fun clickCreateNewBandBtn() = emitEvent(HomeClickEvent.CreateNewBandBtnClick)

}