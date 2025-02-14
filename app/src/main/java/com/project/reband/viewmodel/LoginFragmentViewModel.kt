package com.project.reband.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.user.UserData
import com.project.reband.network.etc.EtcRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragmentViewModel: ViewModel() {
    private val etcRepository = EtcRepository()

    private val _userInfo = MutableStateFlow<UserData?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _loginResult = MutableStateFlow<LoginResult?>(null)
    val loginResult = _loginResult.asStateFlow()

    fun getUserInfoKakao(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            loginLoading()
            etcRepository.getUserInfoKakao(accessToken, refreshToken).collectLatest {
                _userInfo.emit(it.data)
            }
        }
    }

    fun getUserInfoNaver(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            loginLoading()
            etcRepository.getUserInfoNaver(accessToken, refreshToken).collectLatest {
                _userInfo.emit(it.data)
                Log.d("tngur", "로그인 성공 token ${it.data}")
            }
        }
    }

    fun setLoginResult(result: LoginResult) {
        viewModelScope.launch {
            _loginResult.emit(result)
        }
    }

    fun loginLoading() = setLoginResult(LoginResult.Loading)
    fun loginSuccess() = setLoginResult(LoginResult.Success)
    fun loginError() = setLoginResult(LoginResult.Error)

}

sealed interface LoginResult {
    object Loading : LoginResult
    object Success : LoginResult
    object Error : LoginResult
}