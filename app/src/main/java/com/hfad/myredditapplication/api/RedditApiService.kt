package com.hfad.myredditapplication.api

import com.hfad.myredditapplication.pojo.RedditResponse
import com.hfad.myredditapplication.utils.Constants.REDDIT_API_END_POINT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApiService {

    @GET(REDDIT_API_END_POINT)
    fun getRedditResponse(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Single<RedditResponse>
}