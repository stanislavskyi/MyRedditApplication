package com.hfad.myredditapplication.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hfad.myredditapplication.R

class RedditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.findViewById<ImageView>(R.id.imageViewPopularPost)
    val author = itemView.findViewById<TextView>(R.id.textViewAuthor)
    val numComments = itemView.findViewById<TextView>(R.id.textViewNumComments)
    val time = itemView.findViewById<TextView>(R.id.textViewTime)
}