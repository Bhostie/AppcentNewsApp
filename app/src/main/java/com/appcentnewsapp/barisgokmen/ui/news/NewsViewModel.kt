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


    private val _newsArticles = MutableLiveData<List<ArticlesItem>>()
    val newsArticles: LiveData<List<ArticlesItem>> = _newsArticles

    fun searchNews(query: String) {
        newsRepository.searchNews(query, this)
    }

    // Called when news articles are successfully fetched
    fun onNewsFetched(newsArticles: List<ArticlesItem?>?) {
        // Process the fetched news articles and update the UI
        Log.d("onNewsFetched","onNewsFetched RUNNED")

        // Filter out any null items from the list
        val filteredArticles = newsArticles?.filterNotNull() ?: emptyList()

        // Update the LiveData with the filtered list of news articles
        _newsArticles.value = filteredArticles

    }

    // Called when there is an error while fetching news articles
    fun onNewsFetchError(errorMessage: String?) {
        // Handle the error and update the UI accordingly
    }


}