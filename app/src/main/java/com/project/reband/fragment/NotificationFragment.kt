package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.reband.adapter.NotificationAdapter
import com.project.reband.databinding.FragmentNotificationBinding
import com.project.reband.viewmodel.NotificationFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationFragment: Fragment() {

    private val binding : FragmentNotificationBinding by lazy {
        FragmentNotificationBinding.inflate(layoutInflater)
    }

    private val viewModel : NotificationFragmentViewModel by viewModels()
    private val notificationAdapter by lazy { NotificationAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.getNotification()
        }

        lifecycleScope.launch {
            viewModel.notificationList.collectLatest {
                notificationAdapter.submitList(it)
            }
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding.apply {
            tvBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            rvNotification.apply {
                adapter = notificationAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

        }

        return binding.root
    }
}