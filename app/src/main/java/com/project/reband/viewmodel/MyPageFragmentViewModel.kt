package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.etc.NoticeData
import com.project.reband.data.etc.TermsOfServiceData
import com.project.reband.data.user.UserData
import com.project.reband.network.etc.EtcRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyPageFragmentViewModel : ViewModel() {
    private val etcRepository = EtcRepository()

    private val _loginState = MutableStateFlow<LoginState?>(null)
    val loginState = _loginState.asStateFlow()

    private val _termsOfService = MutableStateFlow<TermsOfServiceData.TermsOfServiceList?>(null)
    val termsOfService = _termsOfService.asStateFlow()

    private val _notice = MutableStateFlow<NoticeData.NoticeList?>(null)
    val notice = _notice.asStateFlow()

    private fun setLoginState(state: LoginState) {
        viewModelScope.launch {
            _loginState.emit(state)
        }
    }

    fun setLogin() {
        setLoginState(LoginState.isLogin)
    }

    fun setLogout() {
        setLoginState(LoginState.isLogout)
    }

    fun getTermsOfService() {
        viewModelScope.launch {
            etcRepository.getTermsOfService().collectLatest {
                _termsOfService.emit(it.data)
            }
        }
    }

    fun getNotice() {
        viewModelScope.launch {
            etcRepository.getNotice().collectLatest {
                _notice.emit(it.data)
            }
        }
    }

}

sealed interface LoginState {
    object isLogout : LoginState
    object isLogin : LoginState
}