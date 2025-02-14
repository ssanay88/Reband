package com.project.reband.viewholder

import android.util.Log
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
            Glide.with(ivBandThumbnail.context)
                .load(item.imageUrl)
                .into(ivBandThumbnail)
        }
    }

    fun goToDetailPage(bandNo: Int) {
        handler?.goToHiringDetail(bandNo)
    }

}