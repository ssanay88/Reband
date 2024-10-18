package com.example.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.reband.databinding.VhPoolSortBinding

class PoolSortVH(
    private val binding: VhPoolSortBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            btnSortByFilter.setOnClickListener {

            }

            btnSortByLatest.setOnClickListener {

            }

            btnSortByRegister.setOnClickListener {

            }
        }
    }

}