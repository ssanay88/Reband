package com.project.reband.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.etc.TermsOfServiceData
import com.project.reband.databinding.VhTermsofuseBinding

class TermsOfUseVH(
    private val binding: VhTermsofuseBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TermsOfServiceData.TermsOfServiceList.TermsOfService) {
        binding.apply {
            this.item = item
        }
    }

}