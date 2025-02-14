package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.etc.TermsOfServiceData.TermsOfServiceList.TermsOfService
import com.project.reband.databinding.VhTermsofuseBinding
import com.project.reband.viewholder.RecruitingBandVH
import com.project.reband.viewholder.TermsOfUseVH

class TermsOfUseAdapter (
    private val data: MutableList<TermsOfService>,
) :RecyclerView.Adapter<TermsOfUseVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsOfUseVH {
        val inflater = LayoutInflater.from(parent.context)
        return TermsOfUseVH(VhTermsofuseBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TermsOfUseVH, position: Int) {
        holder.bind(data[position])
    }



}