package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.recruitment.HiringData
import com.project.reband.data.recruitment.RecruitmentDetailData
import com.project.reband.network.recruitment.RecruitmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HiringDetailFragmentViewModel : ViewModel() {
    private val recruitmentRepository = RecruitmentRepository()

    private val _recruitmentDetail = MutableStateFlow<RecruitmentDetailData.RecruitmentDetail?>(null)
    val recruitmentDetail = _recruitmentDetail.asStateFlow()

    fun getRecruitmentDetail(recruitmentNo: Int) {
        viewModelScope.launch {
            recruitmentRepository.getRecruitmentDetail(recruitmentNo).collectLatest {
                _recruitmentDetail.emit(it)
            }
        }
    }

    fun applyRecruitment(recruitmentNo: Int) {
        viewModelScope.launch {
            recruitmentRepository.applyRecruitment(recruitmentNo)
        }
    }

}