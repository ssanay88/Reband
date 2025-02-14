package com.project.reband.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.project.reband.GlobalApplication
import com.project.reband.R
import com.project.reband.databinding.FragmentLoginBinding
import com.project.reband.viewmodel.LoginFragmentViewModel
import com.project.reband.viewmodel.LoginResult
import com.project.reband.viewmodel.MyPageFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val viewModel : LoginFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycleScope.launch {
            viewModel.userInfo.collectLatest {
                it?.let {
                    GlobalApplication.getInstance().getDataStore().apply {
                        setJwtToken(it.jwtToken)
                        setInstrument(it.memberInfo.instrument)
                        setNickName(it.memberInfo.nickName)
                        setExperience(it.memberInfo.experience)
                        setBandNo(it.bandInfo.bandNo)
                        setUserGrade(it.bandInfo.grade)
                        setBandName(it.bandInfo.bandName)
                    }
                    viewModel.loginSuccess()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loginResult.collectLatest { result ->
                when (result) {
                    is LoginResult.Loading -> {
                        binding.icLoading.visibility = View.VISIBLE
                    }
                    is LoginResult.Success -> {
                        binding.icLoading.visibility = View.GONE
                        backToMyPageFragment()
                    }
                    else -> {
                        binding.icLoading.visibility = View.GONE
                    }
                }
            }
        }


        binding.apply {

            btnBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            // 카카오 로그인
            btnKakaoLogin.setOnClickListener {
                context?.let {
                    kakaoLogin(it)
                }
            }

            // 네이버 로그인
            btnNaverLogin.setOAuthLogin(oauthLoginCallback = object : OAuthLoginCallback {
                override fun onError(errorCode: Int, message: String) {
                    Log.d("tngur", "네이버 로그인 실패 onError : ${message}")
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    Log.d("tngur", "네이버 로그인 실패 onFailure: ${message}")
                }

                override fun onSuccess() {
                    val accessToken = NaverIdLoginSDK.getAccessToken() ?: ""
                    val refreshToken = NaverIdLoginSDK.getRefreshToken() ?: ""
                    if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                        Log.d("tngur", "naver - access : ${accessToken} , refresh : ${refreshToken}")
                        viewModel.getUserInfoNaver(accessToken, refreshToken)
                    }

                }

            })
        }

        return binding.root
    }


    private fun kakaoLogin(context: Context) {
        val userApiClient = UserApiClient.instance
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("tngur", "1. 카카오계정으로 로그인 실패", error)
                viewModel.loginError()
            } else if (token != null) {
                Log.i("tngur", "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (userApiClient.isKakaoTalkLoginAvailable(context)) {
            userApiClient.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("tngur", "2. 카카오톡으로 로그인 실패", error)
                    viewModel.loginError()

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    userApiClient.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i("tngur", "카카오톡으로 로그인 성공 ${token.accessToken} , ${token.refreshToken}")
                    viewModel.getUserInfoKakao(token.accessToken, token.refreshToken)

                }
            }
        } else {
            userApiClient.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun backToMyPageFragment() {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.main_fragment_container, MyPageFragment()).commit()
    }
}