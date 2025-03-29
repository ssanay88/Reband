package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import com.project.reband.network.recruitment.RecruitmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HiringRegisterViewModel @Inject constructor(
    private val recruitmentRepository: RecruitmentRepository
): ViewModel() {



}