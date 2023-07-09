package com.appcentnewsapp.barisgokmen.ui.likes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.data.repository.LocalNewsRepository

class LikedNewsViewModel : ViewModel() {

    private val localNewsRepository = LocalNewsRepository()
    private val _newsArticles = MutableLiveData<List<ArticlesItem>?>()
    val likedNewsList: LiveData<List<ArticlesItem>?> = _newsArticles

    fun getLocalNews() {
        localNewsRepository.getLocalNews(this)
    }
    fun onNewsFetched(newsArticles: List<ArticlesItem?>?) {
        if (newsArticles != null) {
            _newsArticles.value = newsArticles.filterNotNull()
        }
    }
}