package com.project.reband.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.project.reband.databinding.ItemSpinnerLocationBinding

class LocationSpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val menuList: Array<String>
): ArrayAdapter<String>(context, resId, menuList) {

    // 드롭 다운하지 않은 상태의 Spinner 항목의 뷰
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.tvLocationSpinner.text = menuList[position]

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.tvLocationSpinner.text = menuList[position]

        return binding.root
    }

    override fun getCount(): Int = menuList.size

}