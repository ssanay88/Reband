package com.project.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.project.reband.databinding.VhHireSortBinding
import com.project.reband.fragment.HiringFragment
import com.project.reband.viewmodel.HiringFragmentViewModel

class HireSortVH(
    private val binding: VhHireSortBinding,
    private val viewModel: HiringFragmentViewModel,
    private val handler: HiringFragment.ClickHandler? = null
) : RecyclerView.ViewHolder(binding.root){


    init {
        binding.apply {
            this.vh = this@HireSortVH
            vm = viewModel
        }
    }
}