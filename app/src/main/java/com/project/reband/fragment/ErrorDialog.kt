package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.reband.databinding.ErrorDialogBinding

class ErrorDialog(
    private val title: String,
    private val content: String,
) : DialogFragment() {

    private var _binding: ErrorDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ErrorDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            tvErrorDialogTitle.text = title
            tvErrorDialogContent.text = content
        }

        binding.tvErrorDialogConfirm.setOnClickListener {
            dismiss()
        }

        dialog?.apply {
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}