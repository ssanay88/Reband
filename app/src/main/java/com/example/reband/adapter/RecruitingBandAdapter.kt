package com.example.reband.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reband.data.RecruitingBandData
import com.example.reband.databinding.VhRecruitingBandBinding
import com.example.reband.viewholder.RecruitingBandVH

class RecruitingBandAdapter: ListAdapter<RecruitingBandData, RecyclerView.ViewHolder>(diff) {

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
        return RecruitingBandVH(VhRecruitingBandBinding.inflate(LayoutInflater.from(parent.context)))
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