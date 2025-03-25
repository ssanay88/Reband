package com.project.reband.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.project.reband.data.user.UserData
import com.project.reband.network.etc.EtcRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val etcRepository: EtcRepository
): ViewModel() {

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

    fun startKakaoLogin(context: Context) {

        val userApiClient = UserApiClient.instance
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("tngur", "1. 카카오계정으로 로그인 실패", error)
                loginError()
            } else if (token != null) {
                Log.i("tngur", "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (userApiClient.isKakaoTalkLoginAvailable(context)) {
            userApiClient.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        loginError()
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    userApiClient.loginWithKakaoAccount(context, callback = callback)
                    loginError()
                } else if (token != null) {
                    Log.i("tngur", "카카오톡으로 로그인 성공 ${token.accessToken} , ${token.refreshToken}")
                    getUserInfoKakao(token.accessToken, token.refreshToken)
                }
            }
        } else {
            userApiClient.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun startNaverLogin(context: Context) {

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(context,"로그인 에러", Toast.LENGTH_LONG).show()
                loginError()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Toast.makeText(context,"로그인 실패", Toast.LENGTH_LONG).show()
                loginError()
            }

            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행
                val accessToken = NaverIdLoginSDK.getAccessToken() ?: ""
                val refreshToken = NaverIdLoginSDK.getRefreshToken() ?: ""
                if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                    getUserInfoNaver(accessToken, refreshToken)
                }
            }

        }
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }

}

sealed interface LoginResult {
    object Loading : LoginResult
    object Success : LoginResult
    object Error : LoginResult
}