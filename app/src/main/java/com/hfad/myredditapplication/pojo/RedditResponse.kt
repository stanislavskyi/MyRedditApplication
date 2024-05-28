package com.hfad.myredditapplication.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RedditResponse(
    @SerializedName("data")
    @Expose
    val data: RedditData
)
