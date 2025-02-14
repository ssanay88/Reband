package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.etc.NoticeData
import com.project.reband.databinding.VhNoticeBinding
import com.project.reband.viewholder.NoticeVH
import com.project.reband.viewholder.TermsOfUseVH

class NoticeAdapter(
    private val data: MutableList<NoticeData.NoticeList.Notice>
) : RecyclerView.Adapter<NoticeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeVH {
        val inflater = LayoutInflater.from(parent.context)
        return NoticeVH(VhNoticeBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NoticeVH, position: Int) {
        holder.bind(data[position])
    }

}