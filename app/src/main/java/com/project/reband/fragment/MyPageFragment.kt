package com.project.reband.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.project.reband.databinding.FragmentMyPageBinding
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.project.reband.GlobalApplication
import com.project.reband.R
import com.project.reband.viewmodel.LoginState
import com.project.reband.viewmodel.MyPageFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch


class MyPageFragment() : Fragment() {

    companion object {
        private const val STATE_LOGOUT = "state_logout"
        private const val STATE_PROFILE_INCOMPLETE = "state_profile_incomplete"
        private const val STATE_PROFILE_COMPLETE = "state_profile_complete"
    }

    private val myPageBinding: FragmentMyPageBinding by lazy {
        FragmentMyPageBinding.inflate(layoutInflater)
    }

    private val viewModel : MyPageFragmentViewModel by viewModels()
    private val dataStore = GlobalApplication.getInstance().getDataStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.getTermsOfService()
        viewModel.getNotice()

        lifecycleScope.launch {
            dataStore.jwtToken.zip(dataStore.instrument) { token, instrument ->
                val loginState = if (token.isEmpty()) {
                       STATE_LOGOUT
                } else if (instrument.isEmpty()) {
                    STATE_PROFILE_INCOMPLETE
                } else {
                    STATE_PROFILE_COMPLETE
                }
                loginState
            }.collectLatest { state ->
                when (state) {
                    STATE_LOGOUT -> {
                        myPageBinding.apply {
                            clStateLogout.visibility = View.VISIBLE
                            clStateProfileComplete.visibility = View.GONE
                            clStateProfileIncomplete.visibility = View.GONE
                            btnLogout.visibility = View.GONE
                        }
                    }
                    STATE_PROFILE_INCOMPLETE -> {
                        myPageBinding.apply {
                            clStateLogout.visibility = View.GONE
                            clStateProfileComplete.visibility = View.GONE
                            clStateProfileIncomplete.visibility = View.VISIBLE
                            btnLogout.visibility = View.VISIBLE
                        }
                    }
                    STATE_PROFILE_COMPLETE -> {
                        myPageBinding.apply {
                            clStateLogout.visibility = View.GONE
                            clStateProfileComplete.visibility = View.VISIBLE
                            clStateProfileIncomplete.visibility = View.GONE
                            btnLogout.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }


        myPageBinding.apply {
            btnStateLogout.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_fragment_container, LoginFragment())?.commit()
            }

            // 밴드 관리
            tvBandManagement.setOnClickListener {
                lifecycleScope.launch {
                    if (dataStore.jwtToken.first().isNotEmpty()) {
                        if (dataStore.instrument.first().isEmpty()) {
                            val confirmDialog = ConfirmDialog(
                                title = "오류",
                                content = "프로필 등록 후 이용 가능한 메뉴입니다.\n프로필을 등록하시겠습니까?",
                                confirmCallback = {
                                    goToProfileRegisterFragment()
                                }
                            )
                            confirmDialog.show(requireActivity().supportFragmentManager, "confirmDialog")
                        } else {
                            if (dataStore.bandNo.first().isEmpty()) {
                                val confirmDialog = ConfirmDialog(
                                    title = "오류",
                                    content = "밴드 가입 후 이용 가능한 메뉴입니다.\n신규 밴드를 등록하시겠습니까?",
                                    confirmCallback = {
                                        goToBandRegisterFragment()
                                    }
                                )
                                confirmDialog.show(requireActivity().supportFragmentManager, "confirmDialog")
                            } else {
                                if (dataStore.userGrade.first() == "LEADER") {
                                    activity?.supportFragmentManager?.beginTransaction()
                                        ?.add(R.id.main_fragment_container, MybandLeaderFragment())?.commit()
                                } else {
                                    activity?.supportFragmentManager?.beginTransaction()
                                        ?.add(R.id.main_fragment_container, MybandMemberFragment())?.commit()
                                }
                            }
                        }
                    } else {
                        val errorDialog = ErrorDialog(
                            title = "오류",
                            content = "로그인 후 이용해주세요"
                        )
                        errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
                    }

                }

            }

            // 지원 현황
            tvApplicationStatus.setOnClickListener {
                lifecycleScope.launch {
                    if (dataStore.jwtToken.first().isNotEmpty()) {
                        if (dataStore.instrument.first().isEmpty()) {
                            val confirmDialog = ConfirmDialog(
                                title = "확인",
                                content = "프로필 등록 후 이용 가능한 메뉴입니다.프로필을 등록하시겠습니까?",
                                confirmCallback = {
                                    goToProfileRegisterFragment()
                                }
                            )
                            confirmDialog.show(requireActivity().supportFragmentManager, "errorDialog")
                        } else {
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.add(R.id.main_fragment_container, MyApplyFragment())?.commit()
                        }

                    } else {
                        val errorDialog = ErrorDialog(
                            title = "오류",
                            content = "로그인 후 이용해주세요"
                        )
                        errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
                    }
                }

            }

            // 알림
            tvNotification.setOnClickListener {
                if (AuthApiClient.instance.hasToken()) {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.main_fragment_container, NotificationFragment())?.commit()
                } else {
                    val errorDialog = ErrorDialog(
                        title = "오류",
                        content = "로그인 후 이용해주세요"
                    )
                    errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
                }
            }

            tvNotice.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.notice.collectLatest {
                        val noticeFragmentDialog = it?.let { data ->
                            NoticeFragmentDialog(data)
                        }
                        noticeFragmentDialog?.show(requireActivity().supportFragmentManager, "NoticeFragmentDialog")
                    }
                }

            }

            tvTermsOfUse.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.termsOfService.collectLatest {
                        val termsOfUseFragmentDialog = it?.let { data ->
                            TermsOfUseFragmentDialog(data)
                        }
                        termsOfUseFragmentDialog?.show(requireActivity().supportFragmentManager, "TermsOfUseFragmentDialog")
                    }
                }
            }

            btnStateProfileIncomplete.setOnClickListener {
                goToProfileRegisterFragment()
            }

            btnLogout.apply {
                setOnClickListener {
                    logout()
                }
            }

        }

        return myPageBinding.root
    }

    private fun goToBandRegisterFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.main_fragment_container, BandRegisterFragment())?.commit()
    }

    private fun goToProfileRegisterFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.main_fragment_container, ProfileRegisterFragment())?.commit()
    }

    private fun logout() {
        // 카카오 로그아웃
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(this.context, "로그아웃 실패 ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this.context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // 네이버 로그아웃
            NaverIdLoginSDK.logout()
        }

        lifecycleScope.launch {
            GlobalApplication.getInstance().getDataStore().apply {
                setJwtToken("")
                setInstrument("")
                setNickName("")
                setExperience("")
                setBandNo("")
                setUserGrade("")
                setBandName("")
            }
        }
    }
}