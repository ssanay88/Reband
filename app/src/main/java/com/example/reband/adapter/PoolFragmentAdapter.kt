package com.example.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reband.data.FindBandData
import com.example.reband.databinding.VhFindBandBinding
import com.example.reband.databinding.VhPoolSortBinding
import com.example.reband.viewholder.FindBandVH
import com.example.reband.viewholder.PoolSortVH

class PoolFragmentAdapter: ListAdapter<FindBandData, RecyclerView.ViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<FindBandData>() {
            override fun areItemsTheSame(
                oldItem: FindBandData,
                newItem: FindBandData
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: FindBandData,
                newItem: FindBandData
            ): Boolean = oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            1 -> PoolSortVH(VhPoolSortBinding.inflate(inflater, parent, false))
            else -> FindBandVH(VhFindBandBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is FindBandVH) {
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1 else 2
    }


}