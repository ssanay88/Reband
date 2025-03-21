package com.project.reband.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakao.sdk.auth.AuthApiClient
import com.project.reband.GlobalApplication
import com.project.reband.R
import com.project.reband.RecruitingBandItemDecoration
import com.project.reband.adapter.RecruitingBandAdapter
import com.project.reband.data.recruitment.HiringData
import com.project.reband.databinding.FragmentHomeBinding
import com.project.reband.event.HomeClickEvent
import com.project.reband.network.recruitment.RecruitmentRepository
import com.project.reband.test.getTestRecruitmentList
import com.project.reband.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val clickHandler = ClickHandler()
    private val dataStore = GlobalApplication.getInstance().getDataStore()

    private val homeBinding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel.apply {
            getRecruitmentListData()
            getHashTagList()
            getInstrumentList()
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val recruitingBandAdapter = RecruitingBandAdapter(viewModel, clickHandler)

        homeBinding.rvRecruitingNewMember.apply {
            adapter = recruitingBandAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            if (itemDecorationCount == 0) {
                addItemDecoration(RecruitingBandItemDecoration())
            }
        }

        lifecycleScope.launch {
            viewModel.recruitmentList.collectLatest {
                // val recruitmentList = it as List<HiringData.Recruitment>?
                // if (recruitmentList != null) recruitingBandAdapter.submitList(if (recruitmentList.size >= 3) recruitmentList.subList(0,3) else recruitmentList)

                // 테스트용 데이터 입력
                val testRecruitmentList = getTestRecruitmentList()
                if (testRecruitmentList != null) recruitingBandAdapter.submitList(if (testRecruitmentList.size >= 3) testRecruitmentList.subList(0,3) else testRecruitmentList)
            }
        }

        lifecycleScope.launch {
            viewModel.clickEvent.collectLatest { event ->
                when (event) {
                    is HomeClickEvent.SearchNewMemberAddBtnClick -> {
                        SearchNewMemberAdd()
                    }
                    is HomeClickEvent.ShowMoreRecruitingBandBtnClick -> {
                        ShowMoreRecruitingBand()
                    }
                    is HomeClickEvent.CreateNewBandBtnClick -> {
                        CreateNewBand()
                    }
                    else -> {}
                }
            }
        }

        init()

        return homeBinding.root
    }

    private fun init() {
        homeBinding.apply {
            vm = viewModel
            lifecycleOwner = this@HomeFragment
        }
    }

    private fun SearchNewMemberAdd() {
        lifecycleScope.launch {
            val userGrade = GlobalApplication.getInstance().getDataStore().userGrade.first()
            val bandNo = GlobalApplication.getInstance().getDataStore().bandNo.first()


            if (AuthApiClient.instance.hasToken()) {
                if (bandNo.isNotEmpty()) {
                    if (userGrade == "LEADER") {

                    } else {
                        val errorDialog = ErrorDialog(
                            title = "오류",
                            content = "모집 공고 작성 권한이 없습니다."
                        )
                        errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
                    }
                } else {
                    val errorDialog = ConfirmDialog(
                        title = "확인",
                        content = "소속된 밴드가 없습니다.\n밴드를 등록 하시겠습니까?",
                        confirmCallback = {}
                    )
                    errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
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

    private fun ShowMoreRecruitingBand() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_fragment_container, HiringFragment())?.commit()
    }

    private fun CreateNewBand() {
        lifecycleScope.launch {
            if (dataStore.jwtToken.first().isNotEmpty()) {
                if (dataStore.bandNo.first().isEmpty()) {
                    val confirmDialog = ConfirmDialog(
                        title = "확인",
                        content = "신규 밴드를 생성하시겠습니까?",
                        confirmCallback = {
                            goToCreateNewBandFragment()
                        }
                    )
                    confirmDialog.show(requireActivity().supportFragmentManager, "confirmDialog")
                } else {
                    val errorDialog = ErrorDialog(
                        title = "오류",
                        content = "이미 가입된 밴드가 있습니다."
                    )
                    errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
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

    private fun goToCreateNewBandFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_fragment_container, BandRegisterFragment())?.commit()
    }

    inner class ClickHandler {
        /**
         * 모집 밴드 클릭
         */
        fun goToHiringDetail(bandNo: Int) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment_container, HiringDetailFragment(bandNo))?.commit()
        }
    }
}