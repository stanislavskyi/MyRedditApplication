package com.hfad.myredditapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.hfad.myredditapplication.R
import com.hfad.myredditapplication.pojo.RedditChild
import com.squareup.picasso.Picasso

class RedditAdapter() :
    PagingDataAdapter<RedditChild, RedditViewHolder>(RedditItemDiffCallback()) {

    var onRedditPostClickListener: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.popular_post_item, parent, false
            )
        return RedditViewHolder(view)
    }

    override fun onBindViewHolder(holder: RedditViewHolder, position: Int) {
        val popularPost = getItem(position)
        holder.author.text =
            "${holder.itemView.resources.getString(R.string.author)} ${popularPost?.childrenData?.author}"
        holder.numComments.text = " ${popularPost?.childrenData?.numComments.toString()}"


        val unixTime = popularPost?.childrenData?.createdUtc?.toLong() ?: 0
        val currentTimeMillis = System.currentTimeMillis()

        val diffMillis = currentTimeMillis - unixTime * 1000
        val diffHours = diffMillis / (1000 * 60 * 60)

        val formattedTime = when {
            diffHours < 1 -> "${holder.itemView.resources.getString(R.string.less_than_an_hour_ago)}"
            diffHours == 1L -> "${holder.itemView.resources.getString(R.string.one_hour_ago)}"
            diffHours < 24 -> "$diffHours ${holder.itemView.resources.getString(R.string.x_hours_ago)}"
            else -> {
                val diffDays = diffHours / 24
                "$diffDays ${holder.itemView.resources.getString(R.string.days_ago)}"
            }
        }

        holder.time.text = formattedTime

        Picasso.get().load(popularPost?.childrenData?.url)
            .centerCrop()
            .fit()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(holder.image)

        holder.image.setOnClickListener {
            onRedditPostClickListener?.invoke(popularPost?.childrenData?.url ?: "")
        }
    }

}