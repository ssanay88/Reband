package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.R
import com.project.reband.data.recruitment.MyApplyEntry
import com.project.reband.databinding.VhMyApplyContentsBinding
import com.project.reband.databinding.VhMyApplyTitleBinding
import com.project.reband.viewholder.MyApplyContentsVH
import com.project.reband.viewholder.MyApplyTitleVH

class MyApplyListAdapter() : ListAdapter<MyApplyEntry, RecyclerView.ViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<MyApplyEntry>() {
            override fun areItemsTheSame(oldItem: MyApplyEntry, newItem: MyApplyEntry): Boolean = when {
                oldItem is MyApplyEntry.MyApply && newItem is MyApplyEntry.MyApply -> oldItem.recruitmentNo == newItem.recruitmentNo
                else -> false
            }

            override fun areContentsTheSame(oldItem: MyApplyEntry, newItem: MyApplyEntry): Boolean = when {
                oldItem is MyApplyEntry.MyApply && newItem is MyApplyEntry.MyApply -> oldItem.recruitmentNo == newItem.recruitmentNo
                else -> false
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.vh_my_apply_title -> MyApplyTitleVH(VhMyApplyTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.vh_my_apply_contents -> MyApplyContentsVH(VhMyApplyContentsBinding.inflate(LayoutInflater.from(parent.context),parent, false))
            else -> throw IllegalArgumentException("Invalid ViewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { myApplyEntry ->
            when (holder) {
                is MyApplyTitleVH -> holder.bind((myApplyEntry as MyApplyEntry.AppliedTitle).toString())
                is MyApplyContentsVH -> holder.bind(myApplyEntry as MyApplyEntry.MyApply)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is MyApplyEntry.AppliedTitle -> R.layout.vh_my_apply_title
            is MyApplyEntry.SuggestedTitle -> R.layout.vh_my_apply_title
            is MyApplyEntry.MyApply -> R.layout.vh_my_apply_contents
        }

}