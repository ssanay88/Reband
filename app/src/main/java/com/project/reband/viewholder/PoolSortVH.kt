package com.project.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.project.reband.databinding.VhPoolSortBinding
import com.project.reband.viewmodel.PoolFragmentViewModel

class PoolSortVH(
    private val binding: VhPoolSortBinding,
    private val viewModel: PoolFragmentViewModel
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            vm = viewModel
        }
    }

}