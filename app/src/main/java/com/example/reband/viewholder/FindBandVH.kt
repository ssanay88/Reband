package com.example.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.reband.data.FindBandData
import com.example.reband.databinding.VhFindBandBinding

class FindBandVH(
    private val binding: VhFindBandBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FindBandData) {
        binding.apply {
            tvPosition.text = item.myPosition + "가 밴드를 찾고있어요"
            tvCareer.text = "${item.career}년차"
            var hashTag = ""
            item.hashTag.forEach {
                hashTag += "#${it} "
            }
            tvKeywordTag.text = hashTag
            tvApplicantDescription.text = item.introduce
        }
    }

}