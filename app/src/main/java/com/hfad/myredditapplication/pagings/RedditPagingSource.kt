package com.hfad.myredditapplication.pagings

import android.util.Log
import io.reactivex.schedulers.Schedulers
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hfad.myredditapplication.api.RedditApiService
import com.hfad.myredditapplication.pojo.RedditChild

class RedditPagingSource(
    private val apiService: RedditApiService,
    private val subreddit: String
): PagingSource<String, RedditChild>(){

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditChild> {
        return try {
            val limit = params.loadSize
            val after = params.key
            val response = apiService.getRedditResponse(subreddit, limit, after)
                .subscribeOn(Schedulers.io())
                .blockingGet()

            Log.d("TEST_OF_LOADING_DATA_POPULAR_POST", "Response: $response")
            val children = response.data?.children ?: emptyList()

            LoadResult.Page(
                data = children,
                prevKey = null,
                nextKey = response.data?.after
            )
        } catch (e: Exception) {
            Log.e("TEST_OF_LOADING_DATA_POPULAR_POST", "Error loading data", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, RedditChild>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.childrenData?.author
        }
    }

}