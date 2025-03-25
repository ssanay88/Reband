package com.project.reband.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.reband.R
import com.project.reband.data.talentpool.PoolData
import com.project.reband.databinding.VhFindBandBinding
import com.project.reband.fragment.PoolFragment

class FindBandVH(
    private val binding: VhFindBandBinding,
    private val handler: PoolFragment.ClickHandler? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PoolData.TalentPool) {
        binding.apply {
            this.vh = this@FindBandVH
            this.item = item

            tvPosition.text = item.title

            tvCareer.text = "${item.experience}년차"

            if (item.hashTagList == null || item.hashTagList.isEmpty()) {
                tvKeywordTag.visibility = View.GONE
            } else {
                var hashTag = ""
                item.hashTagList.forEach {
                    hashTag += "#${it} "
                }
                tvKeywordTag.text = hashTag
            }

            tvApplicantDescription.text = item.content

            Glide.with(ivPositionThumbnail.context)
                .load(item.image)
                .into(ivPositionThumbnail)

        }
    }

    fun goToPoolDetail(poolNo: Int) {
        handler?.goToPoolDetail(poolNo)
    }

}