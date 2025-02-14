package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.recruitment.HiringData
import com.project.reband.databinding.VhRecruitingBandBinding
import com.project.reband.fragment.HomeFragment
import com.project.reband.viewholder.RecruitingBandVH
import com.project.reband.viewmodel.HomeFragmentViewModel

class RecruitingBandAdapter(
    private val viewModel: HomeFragmentViewModel,
    private val handler: HomeFragment.ClickHandler
): ListAdapter<HiringData.Recruitment, RecyclerView.ViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<HiringData.Recruitment>() {
            override fun areItemsTheSame(
                oldItem: HiringData.Recruitment,
                newItem: HiringData.Recruitment
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: HiringData.Recruitment,
                newItem: HiringData.Recruitment
            ): Boolean = oldItem.bandNo == newItem.bandNo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecruitingBandVH(VhRecruitingBandBinding.inflate(LayoutInflater.from(parent.context)), viewModel, handler)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is RecruitingBandVH) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}