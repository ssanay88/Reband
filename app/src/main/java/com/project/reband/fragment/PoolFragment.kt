package com.project.reband.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.reband.R
import com.project.reband.adapter.PoolFragmentAdapter
import com.project.reband.data.recruitment.HiringData
import com.project.reband.data.talentpool.PoolData
import com.project.reband.databinding.FragmentPoolBinding
import com.project.reband.viewmodel.PoolFragmentViewModel
import com.project.reband.viewmodel.PoolSortBtnClickEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PoolFragment : Fragment() {

    private val poolBinding: FragmentPoolBinding by lazy {
        FragmentPoolBinding.inflate(layoutInflater)
    }

    private val viewModel: PoolFragmentViewModel by viewModels()
    private val clickHandler = ClickHandler()
    private var poolFragmentList = mutableListOf<PoolData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apply {
            getTalentPool()
        }

        viewModel.viewModelScope.launch {
            viewModel.btnClickHandler.collectLatest { event ->
                when (event) {
                    is PoolSortBtnClickEvent.ClickFilterBtn -> {
                        val fragmentManager = requireActivity().supportFragmentManager
                        val transaction = fragmentManager.beginTransaction()
                        transaction
                            .replace(R.id.main_fragment_container, SearchPoolFilterFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    is PoolSortBtnClickEvent.ClickSortLatestBtn -> {
                        // TODO 최신순 정렬
                    }
                    is PoolSortBtnClickEvent.ClickSortRegisterBtn -> {
                        // TODO 등록순 정렬
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val poolFragmentAdapter = PoolFragmentAdapter(viewModel, clickHandler)

        poolBinding.rvPoolFragment.apply {
            adapter = poolFragmentAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        lifecycleScope.launch {
            viewModel.talentPool.collectLatest {
                if (it != null) {
                    val list = mutableListOf<PoolData>().apply {
                        add(PoolData.TalentPoolSortData)
                        addAll(it)
                    }
                    poolFragmentList.apply {
                        clear()
                        addAll(list)
                    }
                    poolFragmentAdapter.submitList(list)
                }
            }
        }

        return poolBinding.root
    }

    inner class ClickHandler {
        /**
         * 인재풀 클릭
         */
        fun goToPoolDetail(poolNo: Int) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment_container, PoolDetailFragment(poolNo))?.commit()
        }
    }
}