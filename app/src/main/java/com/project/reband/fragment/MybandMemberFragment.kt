package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.reband.databinding.FragmentMyBandMemberBinding
import com.project.reband.viewmodel.MybandMemberBtnClickEvent
import com.project.reband.viewmodel.MybandMemberFragmentViewModel
import kotlinx.coroutines.launch

class MybandMemberFragment: Fragment() {

    private val binding : FragmentMyBandMemberBinding by lazy {
        FragmentMyBandMemberBinding.inflate(layoutInflater)
    }

    private val viewModel : MybandMemberFragmentViewModel by viewModels()

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
                    MybandMemberBtnClickEvent.ClickBackBtn -> {

                    }

                    MybandMemberBtnClickEvent.ClickBandInfoInquiryBtn -> {

                    }

                    MybandMemberBtnClickEvent.ClickBandWithdrawalBtn -> {

                    }
                }
            }
        }

        return binding.root
    }

}