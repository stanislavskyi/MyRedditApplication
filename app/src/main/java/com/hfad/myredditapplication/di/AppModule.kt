package com.hfad.myredditapplication.di

import com.hfad.myredditapplication.api.RedditApiService
import com.hfad.myredditapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.REDDIT_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(REDDIT_BASE_URL: String): RedditApiService =
        Retrofit.Builder()
            .baseUrl(REDDIT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RedditApiService::class.java)
}