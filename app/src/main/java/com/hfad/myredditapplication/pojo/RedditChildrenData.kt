package com.hfad.myredditapplication.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RedditChildrenData(
    @SerializedName("author")
    @Expose
    val author: String? = null,

    @SerializedName("created_utc")
    @Expose
    val createdUtc: Int? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("num_comments")
    @Expose
    val numComments: Int? = null
)
