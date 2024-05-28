package com.hfad.myredditapplication.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.hfad.myredditapplication.utils.Constants.REDDIT_BASE_URL

object RedditApiFactory {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(REDDIT_BASE_URL)
        .build()

    val redditApiService = retrofit.create(RedditApiService::class.java)
}