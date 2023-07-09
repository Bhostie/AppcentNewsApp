package com.appcentnewsapp.barisgokmen.data.repository

import com.appcentnewsapp.barisgokmen.data.model.NewsResponse
import com.appcentnewsapp.barisgokmen.data.network.service.NewsService
import com.appcentnewsapp.barisgokmen.ui.newsList.NewsViewModel
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
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    viewModel.onNewsFetchError(errorMessage)
                }
            }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewModel.onNewsFetchError(t.message)
            }
        })
    }
    // This is written by copilot, not me :)
    private fun parseErrorMessage(errorBody: String?): String {
        // Parse the errorBody and extract the actual error message
        return try {
            val startIndex = errorBody?.indexOf("message") ?: -1
            val endIndex = errorBody?.indexOf("\"", startIndex + 10) ?: -1
            errorBody?.substring(startIndex + 10, endIndex) ?: "Failed to retrieve news. Please try again."
        } catch (e: Exception) {
            "Failed to retrieve news. Please try again."
        }
    }

}