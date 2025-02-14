package com.project.reband.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.project.reband.databinding.FragmentHiringBandInfoBinding
import com.project.reband.viewmodel.HiringBandInfoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HiringBandInfoFragment(
    private val bandNo: Int
) : Fragment() {

    private val binding : FragmentHiringBandInfoBinding by lazy {
        FragmentHiringBandInfoBinding.inflate(layoutInflater)
    }

    private val viewModel : HiringBandInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.getBandDetail(bandNo)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            viewModel.bandDetail.collectLatest { data ->

                binding.apply {
                    if (data != null) {
                        Glide.with(ivBandThumbnail.context)
                        .load(data.imageUrl)
                        .into(ivBandThumbnail)

                        tvBandName.text = data.bandName

                        // 밴드 특징

                        tvBandIntroduce.text = if (data.introduce == "") "등록된 밴드 소개가 없습니다." else data.introduce

                        tvBandMemberCount.text = data.bandMemberList.size.toString() + "명"

                        tvGuitarCount.text = data.bandMemberList.filter { it.instrumentNo == 1 }.size.toString()
                        tvDrumCount.text = data.bandMemberList.filter { it.instrumentNo == 2 }.size.toString()
                        tvBassCount.text = data.bandMemberList.filter { it.instrumentNo == 3 }.size.toString()
                        tvVocalCount.text = data.bandMemberList.filter { it.instrumentNo == 4 }.size.toString()
                        tvKeyboardCount.text = data.bandMemberList.filter { it.instrumentNo == 5 }.size.toString()

                        llBandVideo.visibility = if (data.mediaUrl.isEmpty()) View.GONE else View.VISIBLE
                        tvBandVideo.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.mediaUrl))
                            startActivity(intent)
                        }
                    }
                }
            }
        }


        return binding.root
    }

}