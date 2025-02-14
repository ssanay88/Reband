package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.reband.adapter.NoticeAdapter
import com.project.reband.adapter.TermsOfUseAdapter
import com.project.reband.data.etc.NoticeData
import com.project.reband.databinding.ScrollFragmentDialogBinding

class NoticeFragmentDialog(
    private val data: NoticeData.NoticeList
) : DialogFragment() {

    private lateinit var binding: ScrollFragmentDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScrollFragmentDialogBinding.inflate(inflater, container, false)

        binding.rvFragmentDialog.apply {
            adapter = NoticeAdapter(data.noticeList)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}