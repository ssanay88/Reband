package com.project.reband.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.reband.R
import com.project.reband.adapter.HiringFragmentAdapter
import com.project.reband.data.recruitment.HiringData
import com.project.reband.databinding.FragmentHiringBinding
import com.project.reband.test.getTestRecruitmentList
import com.project.reband.viewmodel.HiringFragmentViewModel
import com.project.reband.viewmodel.HireSortBtnClickEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HiringFragment() : Fragment() {

    private val viewModel: HiringFragmentViewModel by viewModels()
    private val clickHandler = ClickHandler()

    private var _binding: FragmentHiringBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewModelScope.launch {
            viewModel.btnClickHandler.collectLatest { event ->
                when (event) {
                    is HireSortBtnClickEvent.ClickFilterBtn -> {
                        val fragmentManager = requireActivity().supportFragmentManager
                        val transaction = fragmentManager.beginTransaction()
                        transaction.replace(R.id.main_fragment_container, SearchBandFilterFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                    is HireSortBtnClickEvent.ClickSortLatestBtn -> {
                        // TODO 최신순 정렬
                        lifecycleScope.launch {
                            val list = viewModel.recruitmentList.value
                            // list?.map { it.updateDate }
                        }

                    }
                    is HireSortBtnClickEvent.ClickSortRegisterBtn -> {
                        // TODO 등록순 정렬
                        lifecycleScope.launch {
                            val list = viewModel.recruitmentList.value
                            // list?.map { it.updateDate }
                        }
                    }
                }
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hiring,
            container,
            false
        )

        viewModel.apply {
            getRecruitmentList()
        }

        val hiringBandAdapter = HiringFragmentAdapter(viewModel, clickHandler)

        binding.rvHiringFragment.apply {
            adapter = hiringBandAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        lifecycleScope.launch {
            viewModel.recruitmentList.collectLatest {
                if (it != null) {
//                    val list = mutableListOf<HiringData>().apply {
//                        add(HiringData.HireSortData)
//                        addAll(it)
//                    }
//                    hiringBandAdapter.submitList(list)
                    val list = getTestRecruitmentList()
                    hiringBandAdapter.submitList(list)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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