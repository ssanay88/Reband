package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.reband.adapter.MyApplyListAdapter
import com.project.reband.data.recruitment.MyApplyEntry
import com.project.reband.databinding.FragmentMyApplyBinding
import com.project.reband.viewmodel.MyApplyFragmentViewModel
import kotlinx.coroutines.launch

class MyApplyFragment : Fragment() {

    private val binding : FragmentMyApplyBinding by lazy {
        FragmentMyApplyBinding.inflate(layoutInflater)
    }

    private val viewModel : MyApplyFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {
            rvMyApply.apply {
                adapter = MyApplyListAdapter()
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        lifecycleScope.launch {
            viewModel.myApplyList.collect {
                val appliedList = mutableListOf<MyApplyEntry>(MyApplyEntry.AppliedTitle)
                val suggestedList = mutableListOf<MyApplyEntry>(MyApplyEntry.SuggestedTitle)
                it?.myApplyList?.forEach {
                    if (it.applyState == "지원") {
                        appliedList.add(it)
                    } else {
                        suggestedList.add(it)
                    }
                    (binding.rvMyApply.adapter as MyApplyListAdapter).submitList(appliedList + suggestedList)
                }
            }
        }

        return binding.root
    }

}