package com.project.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.etc.NoticeData
import com.project.reband.databinding.VhNoticeBinding

class NoticeVH(
    private val binding: VhNoticeBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: NoticeData.NoticeList.Notice) {
        binding.apply {
            this.item = item
        }
    }

}