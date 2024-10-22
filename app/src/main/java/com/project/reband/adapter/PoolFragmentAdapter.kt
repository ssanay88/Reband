package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.FindBandData
import com.project.reband.databinding.VhFindBandBinding
import com.project.reband.databinding.VhPoolSortBinding
import com.project.reband.viewholder.FindBandVH
import com.project.reband.viewholder.PoolSortVH
import com.project.reband.viewmodel.HiringFragmentViewModel
import com.project.reband.viewmodel.PoolFragmentViewModel

class PoolFragmentAdapter(
    private val viewModel: PoolFragmentViewModel
): ListAdapter<FindBandData, RecyclerView.ViewHolder>(diff) {

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
            1 -> PoolSortVH(VhPoolSortBinding.inflate(inflater, parent, false), viewModel)
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