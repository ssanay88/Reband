package com.project.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.project.reband.databinding.VhMyApplyTitleBinding

class MyApplyTitleVH(
    private val binding: VhMyApplyTitleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title : String) {
        binding.tvTitle.text = title
    }

}