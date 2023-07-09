package com.appcentnewsapp.barisgokmen.ui.newsList

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
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun callSearchQuery(query: String) {
        newsRepository.searchNews(query, this)
    }
    // Called when news articles are successfully fetched
    fun onNewsFetched(newsArticles: List<ArticlesItem?>?) {

        if (newsArticles != null) {
            // Update the LiveData with the list of news articles
            _newsArticles.value = newsArticles.filterNotNull()

            // Handle Error message. If query is empty, show error message
            if (newsArticles.isEmpty()){
                _errorMessage.value = "No news found"
            }
            else {
                _errorMessage.value = null
            }
        }
    }
    // Called when there is an error while fetching news articles
    fun onNewsFetchError(errorMessage: String?) {
        _errorMessage.value = errorMessage
    }
}