package com.hfad.myredditapplication.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RedditChild(
    @SerializedName("data")
    @Expose
    val childrenData: RedditChildrenData
)
