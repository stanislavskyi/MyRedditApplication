package com.hfad.myredditapplication.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hfad.myredditapplication.pojo.RedditChild

class RedditItemDiffCallback : DiffUtil.ItemCallback<RedditChild>() {
    override fun areItemsTheSame(oldItem: RedditChild, newItem: RedditChild): Boolean {
        return oldItem.childrenData.author == newItem.childrenData.author
    }

    override fun areContentsTheSame(oldItem: RedditChild, newItem: RedditChild): Boolean {
        return oldItem == newItem
    }
}