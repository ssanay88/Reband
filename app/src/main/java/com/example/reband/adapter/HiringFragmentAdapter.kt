package com.example.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reband.data.RecruitingBandData
import com.example.reband.databinding.VhPoolBinding
import com.example.reband.databinding.VhPoolSortBinding
import com.example.reband.viewholder.PoolSortVH
import com.example.reband.viewholder.PoolVH

class HiringFragmentAdapter : ListAdapter<RecruitingBandData, RecyclerView.ViewHolder>(diff) {

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
            1 -> PoolSortVH(VhPoolSortBinding.inflate(inflater, parent, false))
            else -> PoolVH(VhPoolBinding.inflate(inflater, parent, false))
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