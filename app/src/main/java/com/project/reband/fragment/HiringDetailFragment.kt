package com.project.reband.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.AuthApiClient
import com.project.reband.GlobalApplication
import com.project.reband.R
import com.project.reband.data.recruitment.HiringData
import com.project.reband.data.recruitment.RecruitmentDetailData
import com.project.reband.databinding.FragmentHiringDetailBinding
import com.project.reband.viewmodel.HiringDetailFragmentViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HiringDetailFragment(
    private val bandNo : Int
) : Fragment() {

    private val hiringDetailBinding: FragmentHiringDetailBinding by lazy {
        FragmentHiringDetailBinding.inflate(layoutInflater)
    }

    private val viemodel: HiringDetailFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            Log.d("tngur", "getRecruitmentDetail")
            viemodel.getRecruitmentDetail(bandNo)
        }
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycleScope.launch {
            viemodel.recruitmentDetail.collect {
                it?.let {
                    setRecruitmentDetail(it)
                }
            }
        }

        hiringDetailBinding.apply {
            tvBandInfoDetail.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_fragment_container, HiringBandInfoFragment(bandNo))?.commit()
            }

            // 지원하기
            btnContact.setOnClickListener {
                lifecycleScope.launch {
                    val bandNo = GlobalApplication.getInstance().getDataStore().bandNo.first()

                    if (AuthApiClient.instance.hasToken()) {
                        if (bandNo == "") {
                            val confirmDialog = ConfirmDialog(
                                title = "밴드 지원",
                                content = "${viemodel.recruitmentDetail.value?.bandName}에 \n지원하시겠습니까?",
                                confirmCallback = {
                                    viemodel.recruitmentDetail.value?.recruitmentNo?.let { recruitmentNo ->
                                        contactBand(recruitmentNo)
                                    }
                                }
                            )
                            confirmDialog.show(requireActivity().supportFragmentManager, "confirmDialog")
                        } else {
                            val errorDialog = ErrorDialog(
                                title = "밴드 지원 오류",
                                content = "이미 가입된 밴드가 있습니다.\n탈퇴 후 재시도 해주세요."
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

            // 문의하기
            btnInquiry.setOnClickListener {
                openKakaoTalk()
            }

        }

        return hiringDetailBinding.root
    }

    private fun setRecruitmentDetail(data: RecruitmentDetailData.RecruitmentDetail) {
        hiringDetailBinding.apply {
            tvBandName.text = data.bandName
            tvBandContent.text = data.content
            Glide.with(ivBandThumbnail.context)
                .load(data.instrumentNo)
                .into(ivBandThumbnail)
        }
    }

    private fun contactBand(recruitmentNo: Int) {
        viemodel.applyRecruitment(recruitmentNo)
        val errorDialog = ErrorDialog(
            title = "밴드 지원 완료",
            content = "지원이 완료되었습니다.\n밴드장 승인 후 가입 처리됩니다."
        )
        errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
    }

    private fun openKakaoTalk() {
        if (getPackageList()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viemodel.recruitmentDetail.value?.contractUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            val errorDialog = ErrorDialog(
                title = "오류",
                content = "카카오톡이 설치되어 있지 않습니다."
            )
            errorDialog.show(requireActivity().supportFragmentManager, "errorDialog")
        }
    }

    private fun getPackageList(): Boolean {
        var isExist = false
        val packageManager = requireActivity().packageManager ?: return false

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = packageManager.queryIntentActivities(mainIntent, 0)

        try {
            for (app in apps) {
                if (app.activityInfo?.packageName?.startsWith("com.kakao.talk") == true) {
                    isExist = true
                    break
                }
            }
        } catch (e: Exception) {
            isExist = false
        }
        return isExist
    }

}