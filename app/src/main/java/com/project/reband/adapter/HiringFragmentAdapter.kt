package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.recruitment.HiringData
import com.project.reband.databinding.VhHireSortBinding
import com.project.reband.databinding.VhPoolBinding
import com.project.reband.fragment.HiringFragment
import com.project.reband.viewholder.HireSortVH
import com.project.reband.viewholder.PoolVH
import com.project.reband.viewmodel.HiringFragmentViewModel

class HiringFragmentAdapter(
    private val viewModel: HiringFragmentViewModel,
    private val handler: HiringFragment.ClickHandler
) : ListAdapter<HiringData, RecyclerView.ViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<HiringData>() {
            override fun areItemsTheSame(
                oldItem: HiringData,
                newItem: HiringData
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: HiringData,
                newItem: HiringData
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            1 -> HireSortVH(VhHireSortBinding.inflate(inflater, parent, false), viewModel, handler)
            else -> PoolVH(VhPoolBinding.inflate(inflater, parent, false), viewModel, handler)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            when (holder) {
                is HireSortVH -> {}
                is PoolVH -> holder.bind(item as HiringData.Recruitment)
            }
        }
    }


    override fun getItemViewType(position: Int): Int =
        getItem(position)?.let { item ->
            when(item) {
                is HiringData.HireSortData -> 1
                is HiringData.Recruitment -> 2
                else -> -1
            }
        } ?: -1
}