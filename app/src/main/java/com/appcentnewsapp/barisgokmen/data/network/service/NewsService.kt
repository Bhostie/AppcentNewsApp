package com.appcentnewsapp.barisgokmen.data.network.service

import com.appcentnewsapp.barisgokmen.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {


    @GET("v2/everything")
    fun getNews(@Query("q") keyword: String): Call<NewsResponse>?


}