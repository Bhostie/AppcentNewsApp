package com.appcentnewsapp.barisgokmen.data.repository

import com.appcentnewsapp.barisgokmen.ui.likes.LikedNewsViewModel
import com.appcentnewsapp.barisgokmen.utils.SharedPreferencesManager

class LocalNewsRepository() {

    fun getLocalNews(viewModel: LikedNewsViewModel) {
        val newsList =  SharedPreferencesManager.getSavedArticles()
        viewModel.onNewsFetched(newsList)
    }
}