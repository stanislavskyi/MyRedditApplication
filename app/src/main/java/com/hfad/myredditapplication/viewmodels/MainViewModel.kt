package com.hfad.myredditapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hfad.myredditapplication.api.RedditApiFactory
import com.hfad.myredditapplication.pagings.RedditPagingSource

class MainViewModel : ViewModel(){

    val listData = Pager(PagingConfig(pageSize = 20), pagingSourceFactory = { RedditPagingSource(RedditApiFactory.redditApiService, subreddit = "popular")}
    ).flow.cachedIn(viewModelScope)
}