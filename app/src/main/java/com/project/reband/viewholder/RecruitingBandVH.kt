package com.project.reband.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.reband.data.recruitment.HiringData
import com.project.reband.databinding.VhRecruitingBandBinding
import com.project.reband.fragment.HomeFragment
import com.project.reband.viewmodel.HomeFragmentViewModel

class RecruitingBandVH(
    private val binding: VhRecruitingBandBinding,
    private val vm: HomeFragmentViewModel? = null,
    private val handler: HomeFragment.ClickHandler? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HiringData.Recruitment) {
        binding.apply {
            this.vh = this@RecruitingBandVH
            this.item = item

            tvBandName.text = item.bandName

            tvRecruitPosition.text = item.content

            Glide.with(ivBandThumbnail.context)
                .load(item.imageUrl)
                .into(ivBandThumbnail)

            if (item.hashTagList.isEmpty()) {
                tvKeywordTag.visibility = View.INVISIBLE
            } else {
                tvKeywordTag.visibility = View.VISIBLE
                var hashTagText = ""
                item.hashTagList.forEach { target ->
                    hashTagText += "#" + vm?.hashTagList?.find { it.hashTagNo == target }?.name
                }
                tvKeywordTag.text = hashTagText
            }

        }
    }

    fun goToDetailPage(bandNo: Int) {
        handler?.goToHiringDetail(bandNo)
    }

}