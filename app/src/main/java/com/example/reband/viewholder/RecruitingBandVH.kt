package com.example.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.reband.data.RecruitingBandData
import com.example.reband.databinding.VhRecruitingBandBinding

class RecruitingBandVH(
    private val binding: VhRecruitingBandBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecruitingBandData) {
        binding.tvBandName.text = item.bandName
        binding.tvKeywordTag.text = item.hashTag
        binding.tvRecruitPosition.text = item.targetPosition

    }

}