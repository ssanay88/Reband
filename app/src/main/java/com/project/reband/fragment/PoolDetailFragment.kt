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
import com.project.reband.data.talentpool.PoolData
import com.project.reband.databinding.FragmentPoolDetailBinding
import com.project.reband.viewmodel.PoolDetailFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PoolDetailFragment(
    private val poolNo : Int
): Fragment() {

    private val poolDetailBinding: FragmentPoolDetailBinding by lazy {
        FragmentPoolDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: PoolDetailFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            Log.d("tngur", "PoolDetailFragment : ${poolNo}")
            viewModel.getTalentPoolDetail(poolNo)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            viewModel.poolDetail.collectLatest { data ->
                if (data != null) {
                    poolDetailBinding.apply {
                        tvPositionValue.text = data.instrument
                        tvCareerValue.text = data.experience.toString() + "년차"
                        tvRegionValue.text = data.location
                        tvIntroduceValue.text = data.introduce
                        for (day in data.days) {
                            changeDaysView(day)
                        }
                    }
                }
            }
        }

        poolDetailBinding.apply {
            btnInquiry.setOnClickListener {

            }

            btnSuggest.setOnClickListener {
                val confirmDialog = ConfirmDialog(
                    title = "밴드 영입",
                    content = "밴드 가입을 제안 하시겠습니까?",
                    confirmCallback = {
                        suggestJoinToBand(poolNo)
                    }
                )
                confirmDialog.show(requireActivity().supportFragmentManager, "confirmDialog")
            }
        }

        return poolDetailBinding.root
    }

    private fun changeDaysView(day: String) {
        when (day) {
            "MON" -> {
                poolDetailBinding.tvMonday.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
            "TUE" -> {
                poolDetailBinding.tvTuesday.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
            "WED" -> {
                poolDetailBinding.tvWednesday.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
            "THU" -> {
                poolDetailBinding.tvThursday.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
            "FRI" -> {
                poolDetailBinding.tvFriday.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
            "SAT" -> {
                poolDetailBinding.tvSaturday.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
            "SUN" -> {
                poolDetailBinding.tvSunday.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
            else -> {
                poolDetailBinding.tvConferDay.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }

    private fun suggestJoinToBand(poolNo: Int) {
        viewModel.suggestJoinToBand(poolNo)
    }

}