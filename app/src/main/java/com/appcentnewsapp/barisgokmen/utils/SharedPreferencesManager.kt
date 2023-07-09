package com.appcentnewsapp.barisgokmen.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.appcentnewsapp.barisgokmen.AppcentNewsApplication.Companion.context
import com.appcentnewsapp.barisgokmen.data.model.ArticlesItem
import com.appcentnewsapp.barisgokmen.utils.AppConstants.APP_NAME
import com.appcentnewsapp.barisgokmen.utils.AppConstants.DEFAULT_INT_VALUE
import com.google.gson.Gson

object SharedPreferencesManager {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    init {
        prepareSharedPreferences()
    }

    private fun prepareSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
    }

    fun putArticle(key: String?, value: ArticlesItem?) {
        val gson = Gson()
        val json = gson.toJson(value)
        sharedPreferencesEditor.putString(key, json).apply()
    }
    fun getArticle(key: String?): ArticlesItem? {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, ArticlesItem::class.java)
    }
    fun removeArticle(key: String?) {
        sharedPreferencesEditor.remove(key)
        sharedPreferencesEditor.apply()
    }

    fun isArticleSaved(key: String?): Boolean {
        return getArticle(key) != null
    }

    fun getSavedArticles(): List<ArticlesItem> {
        val gson = Gson()
        val savedArticles = mutableListOf<ArticlesItem>()
        for ((key, value) in sharedPreferences.all) {
            val articleJson = value as String
            val article = gson.fromJson(articleJson, ArticlesItem::class.java)
            savedArticles.add(article)

        }
        return savedArticles
    }





}