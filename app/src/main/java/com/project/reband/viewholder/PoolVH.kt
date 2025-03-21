package com.project.reband.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.reband.data.recruitment.HiringData
import com.project.reband.databinding.VhPoolBinding
import com.project.reband.fragment.HiringFragment
import com.project.reband.viewmodel.HiringFragmentViewModel

class PoolVH(
    private val binding: VhPoolBinding,
    private val viewModel: HiringFragmentViewModel,
    private val handler: HiringFragment.ClickHandler? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HiringData.Recruitment) {
        binding.apply {
            this.vh = this@PoolVH
            this.item = item

            tvBandName.text = item.bandName

            tvBandDescription.text = item.content

            tvRecruitPosition.text = when (item.recruitmentNo % 5) {
                0 -> "보컬"
                1 -> "드럼"
                2 -> "기타"
                3 -> "키보드"
                else -> "베이스"
            }

            Glide.with(ivBandThumbnail.context)
                .load(item.imageUrl)
                .into(ivBandThumbnail)

            if (item.hashTagList.isEmpty()) {
                tvKeywordTag.visibility = View.INVISIBLE
            } else {
                tvKeywordTag.visibility = View.VISIBLE
                var hashTagText = ""
                item.hashTagList.forEach { target ->
                    // hashTagText += "#" + vm?.hashTagList?.find { it.hashTagNo == target }?.name
                    // 테스트용 데이터 입력
                    hashTagText += "#" + target
                }
                tvKeywordTag.text = hashTagText
            }

        }
    }

    fun goToDetailPage(bandNo: Int) {
        handler?.goToHiringDetail(bandNo)
    }

}