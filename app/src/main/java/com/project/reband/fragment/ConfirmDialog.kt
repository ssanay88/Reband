package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.reband.databinding.ConfirmDialogBinding

class ConfirmDialog(
    private val title: String,
    private val content: String,
    private val confirmCallback: () -> Unit
) : DialogFragment() {

    private var _binding: ConfirmDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ConfirmDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            tvConfirmDialogTitle.text = title
            tvConfirmDialogContent.text = content
        }

        binding.tvConfirmDialogClose.setOnClickListener {
            dismiss()
        }

        binding.tvConfirmDialogConfirm.setOnClickListener {
            confirmCallback.invoke()
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}