package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.data.etc.NotificationData
import com.project.reband.databinding.VhNotificationBinding
import com.project.reband.viewholder.NotificationVH

class NotificationAdapter : ListAdapter<NotificationData.Notification, RecyclerView.ViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<NotificationData.Notification>() {
            override fun areItemsTheSame(
                oldItem: NotificationData.Notification,
                newItem: NotificationData.Notification
            ): Boolean = oldItem.content == newItem.content

            override fun areContentsTheSame(
                oldItem: NotificationData.Notification,
                newItem: NotificationData.Notification
            ): Boolean = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        return NotificationVH(VhNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NotificationVH) {
            holder.bind(getItem(position) as NotificationData.Notification)
        }
    }
}

