package com.project.reband.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.reband.R
import com.project.reband.databinding.FragmentBandRegisterBinding
import com.project.reband.databinding.FragmentMyBandLeaderBinding
import com.project.reband.viewmodel.MybandLeaderBtnClickEvent
import com.project.reband.viewmodel.MybandLeaderFragmentViewModel
import kotlinx.coroutines.launch

class MybandLeaderFragment : Fragment() {

    private val binding : FragmentMyBandLeaderBinding by lazy {
        FragmentMyBandLeaderBinding.inflate(layoutInflater)
    }

    private val viewModel : MybandLeaderFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {
            this.vm = viewModel
        }

        lifecycleScope.launch {
            viewModel.btnClickHandler.collect { event ->
                when (event) {
                    MybandLeaderBtnClickEvent.ClickBackBtn -> {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    MybandLeaderBtnClickEvent.ClickBandInfoInquiryBtn -> {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.main_fragment_container, MyBandInfoFragment("LEADER"))?.commit()
                    }
                    MybandLeaderBtnClickEvent.ClickBandInfoFixBtn -> {
                        Log.d("tngur", "3")
                        // 밴드 정보 수정
                    }
                    MybandLeaderBtnClickEvent.ClickBandMemberManageBtn -> {
                        Log.d("tngur", "4")
                        // 멤버 모집 관리
                    }
                    MybandLeaderBtnClickEvent.ClickChangeBandLeaderBtn -> {
                        Log.d("tngur", "5")
                        // 밴드장 위임
                    }
                    MybandLeaderBtnClickEvent.ClickBandWithdrawalBtn -> {
                        // 밴드 탈퇴
                        val errorDialog = ErrorDialog(
                            title = "확인",
                            content = "밴드장은 탈퇴할 수 없습니다.\n밴드장 위임을 먼저 진행해주세요."
                        )
                        errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
                    }
                    MybandLeaderBtnClickEvent.ClickBandBreakupBtn -> {
                        Log.d("tngur", "7")
                        // 밴드 해체
                    }

                }

            }
        }

        return binding.root
    }

}