package com.appcentnewsapp.barisgokmen.data.network

import com.appcentnewsapp.barisgokmen.BuildConfig
import com.appcentnewsapp.barisgokmen.data.network.service.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private lateinit var retrofit: Retrofit
    lateinit var newsService: NewsService

    init {
        retrofitBuilder()
        bindServices()
    }
    private fun retrofitBuilder() {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun bindServices() {
        newsService = retrofit.create(NewsService::class.java)
    }
    private fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(createHttpLoggingInterceptor())
        httpClient.addInterceptor(createApiKeyInterceptor())

        return httpClient.build()
    }
    private fun createHttpLoggingInterceptor() : HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return httpLoggingInterceptor
    }
    // API key is added to every request as header for security reasons
    private fun createApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", BuildConfig.API_KEY)
                .build()
            chain.proceed(modifiedRequest)
        }
    }
}