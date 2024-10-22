package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.RecruitingBandData
import com.project.reband.databinding.VhHireSortBinding
import com.project.reband.databinding.VhPoolBinding
import com.project.reband.viewholder.HireSortVH
import com.project.reband.viewholder.PoolVH
import com.project.reband.viewmodel.HiringFragmentViewModel

class HiringFragmentAdapter(
    private val viewModel: HiringFragmentViewModel
) : ListAdapter<RecruitingBandData, RecyclerView.ViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<RecruitingBandData>() {
            override fun areItemsTheSame(
                oldItem: RecruitingBandData,
                newItem: RecruitingBandData
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: RecruitingBandData,
                newItem: RecruitingBandData
            ): Boolean = oldItem.bandName == newItem.bandName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            1 -> HireSortVH(VhHireSortBinding.inflate(inflater, parent, false), viewModel)
            else -> PoolVH(VhPoolBinding.inflate(inflater, parent, false), viewModel)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is PoolVH) {
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1 else 2
    }
}