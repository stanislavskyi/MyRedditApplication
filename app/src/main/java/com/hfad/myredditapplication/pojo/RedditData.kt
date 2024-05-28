package com.hfad.myredditapplication.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RedditData(
    @SerializedName("children")
    @Expose
    val children: List<RedditChild>,

    @SerializedName("after")
    @Expose
    val after: String? = null,

    @SerializedName("before")
    @Expose
    val before: String? = null
)
