package com.example.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.reband.data.RecruitingBandData
import com.example.reband.databinding.VhPoolBinding

class PoolVH(
    private val binding: VhPoolBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecruitingBandData) {
        binding.apply {
            tvBandName.text = item.bandName
            tvKeywordTag.text = item.hashTag
            tvRecruitPosition.text = item.targetPosition
            tvBandDescription.text = item.description
        }
    }

}