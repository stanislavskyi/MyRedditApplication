package com.hfad.myredditapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hfad.myredditapplication.api.RedditApiService
import com.hfad.myredditapplication.pagings.RedditPagingSource
import com.hfad.myredditapplication.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditApiService: RedditApiService
): ViewModel(){

    val listData = Pager(PagingConfig(pageSize = 20), pagingSourceFactory = {
        RedditPagingSource(redditApiService, subreddit = Constants.SUBREDDIT)
    }).flow.cachedIn(viewModelScope)
}