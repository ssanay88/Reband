package com.project.reband.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.etc.NotificationData
import com.project.reband.databinding.VhNotificationBinding

class NotificationVH(
    private val binding: VhNotificationBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NotificationData.Notification) {
        binding.apply {
            this.item = item
        }
    }
}