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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.project.reband.GlobalApplication
import com.project.reband.adapter.BandMemberAdapter
import com.project.reband.databinding.FragmentMyBandInfoBinding
import com.project.reband.viewmodel.MyBandInfoFragmentViewModel
import com.project.reband.viewmodel.ShowDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyBandInfoFragment(
    private val userGrade: String
): Fragment() {

    private val binding : FragmentMyBandInfoBinding by lazy {
        FragmentMyBandInfoBinding.inflate(layoutInflater)
    }

    private val viewModel : MyBandInfoFragmentViewModel by viewModels()
    private val dataStore = GlobalApplication.getInstance().getDataStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.getBandInfo(dataStore.bandNo.first().toInt())    // 밴드 정보 호출
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycleScope.launch {
            viewModel.showDialog.collectLatest { dialog ->
                when (dialog) {
                    is ShowDialog.MemberInfoDialog -> {
                        val memberInfoDialog = BandMemberDetailInfoDialog(dialog.memberInfo)
                        memberInfoDialog.show(
                            requireActivity().supportFragmentManager,
                            "memberInfoDialog"
                        )
                    }

                    is ShowDialog.DeportDialog -> {
                        val confirmDialog = ConfirmDialog(
                            title = "확인",
                            content = "${dialog.name}님을 추방하시겠습니까?",
                            confirmCallback = {
                                deportMember(dialog.bandNo, dialog.memberNo)
                            }
                        )
                        confirmDialog.show(
                            requireActivity().supportFragmentManager,
                            "confirmDialog"
                        )

                    }

                    is ShowDialog.DeportCompleteDialog -> {
                        val dialog = ErrorDialog(
                            title = "확인",
                            content = "정상적으로 처리되었습니다."
                        )
                        dialog.show(requireActivity().supportFragmentManager, "errorDialog")
                    }

                    else -> {}
                }
            }

            lifecycleScope.launch {
                viewModel.bandInfo.collectLatest { data ->
                    if (data != null) {
                        binding.apply {
                            // 밴드명
                            tvBandName.text = data.bandName

                            // 밴드 이미지
                            Glide.with(ivBandThumbnail.context)
                                .load(data.imageUrl)
                                .into(ivBandThumbnail)

                            // 밴드 지역

                            // 밴드 특징

                            // 밴드 멤버
                            if (data.bandMemberList.isNotEmpty()) {
                                rvBandMember.adapter =
                                    BandMemberAdapter(viewModel, userGrade, data.bandMemberList)
                                rvBandMember.layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            }

                            // 밴드 소개
                            tvBandIntroduce.text = data.introduce

                            // 연주 영상
                            tvBandVideo.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.mediaUrl))
                                startActivity(intent)
                            }

                        }
                    }

                }
            }

            binding.apply {
                tvEditBandInfo.visibility = if (userGrade == "MEMBER") View.GONE else View.VISIBLE

                tvEditBandInfo.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().add(
                        android.R.id.content, EditMyBandFragment()
                    ).commit()
                }
            }
        }
        return binding.root
    }

    private fun deportMember(bandNo: Int, memberNo: Int) {
        viewModel.apply {
            deportMember(bandNo, memberNo)
            showDeportCompleteDialog()
        }
    }

}