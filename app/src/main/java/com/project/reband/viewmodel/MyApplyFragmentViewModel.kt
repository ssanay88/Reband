package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.recruitment.MyApplyEntry
import com.project.reband.network.recruitment.RecruitmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyApplyFragmentViewModel : ViewModel() {

    private val repository = RecruitmentRepository()

    private val _myApplyList = MutableStateFlow<MyApplyEntry.MyApplyList?>(null)
    val myApplyList = _myApplyList.asStateFlow()

    fun getMyApplyList() {
        viewModelScope.launch {
            repository.getMyApplyList().collectLatest {
                _myApplyList.emit(it)
            }
        }
    }

}