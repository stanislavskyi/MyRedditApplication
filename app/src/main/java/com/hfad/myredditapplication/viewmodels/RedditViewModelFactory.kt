package com.hfad.myredditapplication.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RedditViewModelFactory(
    private val imageUrl: String,
    private val application: Application
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RedditViewModel::class.java)) {
            return RedditViewModel(imageUrl = imageUrl, application = application) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}