package com.appcentnewsapp.barisgokmen.data.repository

import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.data.model.NewsResponse
import com.appcentnewsapp.barisgokmen.data.network.service.NewsService
import com.appcentnewsapp.barisgokmen.ui.news.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val newsApiService: NewsService) {

    fun searchNews(query: String, viewModel: NewsViewModel) {
        val call = newsApiService.getNews(query)

        call?.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    newsResponse?.let {
                        val newsArticles = it.articles
                        viewModel.onNewsFetched(newsArticles)
                    }
                } else {
                    viewModel.onNewsFetchError(response.message())
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewModel.onNewsFetchError(t.message)
            }
        })
    }
}