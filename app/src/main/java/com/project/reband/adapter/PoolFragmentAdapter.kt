package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.talentpool.PoolData
import com.project.reband.databinding.VhFindBandBinding
import com.project.reband.databinding.VhPoolSortBinding
import com.project.reband.fragment.PoolFragment
import com.project.reband.viewholder.FindBandVH
import com.project.reband.viewholder.PoolSortVH
import com.project.reband.viewmodel.PoolFragmentViewModel

class PoolFragmentAdapter(
    private val viewModel: PoolFragmentViewModel,
    private val handler: PoolFragment.ClickHandler
) : ListAdapter<PoolData, RecyclerView.ViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<PoolData>() {
            override fun areItemsTheSame(
                oldItem: PoolData,
                newItem: PoolData
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: PoolData,
                newItem: PoolData
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> PoolSortVH(VhPoolSortBinding.inflate(inflater, parent, false), viewModel, handler)
            else -> FindBandVH(VhFindBandBinding.inflate(inflater, parent, false), handler)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            when (holder) {
                is PoolSortVH -> {}
                is FindBandVH -> {
                    holder.bind(item as PoolData.TalentPool)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        getItem(position)?.let { item ->
            when (item) {
                is PoolData.TalentPoolSortData -> 1
                is PoolData.TalentPool -> 2
                else -> -1
            }
        } ?: -1
}