package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.recruitment.MyApplyEntry
import com.project.reband.network.recruitment.RecruitmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyApplyFragmentViewModel @Inject constructor(
    private val repository: RecruitmentRepository
) : ViewModel() {

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