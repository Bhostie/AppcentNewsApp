package com.appcentnewsapp.barisgokmen.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.data.network.RetrofitManager
import com.appcentnewsapp.barisgokmen.data.repository.NewsRepository

class NewsViewModel : ViewModel() {

    private val newsRepository = NewsRepository(RetrofitManager.newsService)
    private val _newsArticles = MutableLiveData<List<ArticlesItem>?>()
    val newsArticles: LiveData<List<ArticlesItem>?> = _newsArticles

    fun searchNews(query: String) {
        newsRepository.searchNews(query, this)
    }

    // Called when news articles are successfully fetched
    fun onNewsFetched(newsArticles: List<ArticlesItem?>?) {

        if (newsArticles != null) {
            // Process the fetched news articles and update the UI
            Log.d("onNewsFetched", "onNewsFetched RUN")

            // Update the LiveData with the list of news articles
            _newsArticles.value = newsArticles.filterNotNull()
        }
    }

    // Called when there is an error while fetching news articles
    fun onNewsFetchError(errorMessage: String?) {
        // Handle the error and update the UI accordingly
    }


}