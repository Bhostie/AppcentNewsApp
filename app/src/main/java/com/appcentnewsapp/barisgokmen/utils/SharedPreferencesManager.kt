package com.appcentnewsapp.barisgokmen.utils

import android.content.Context
import android.content.SharedPreferences
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

    fun putString(key: String, value: String) {
        sharedPreferencesEditor.putString(key, value).apply()
    }

    fun putInt(key: String, value: Int) {
        sharedPreferencesEditor.putInt(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, DEFAULT_INT_VALUE)
    }

    fun putArticle(key: String, value: ArticlesItem) {
        val gson = Gson()
        val json = gson.toJson(value)
        sharedPreferencesEditor.putString(key, json).apply()
    }
    fun getArticle(key: String): ArticlesItem? {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, ArticlesItem::class.java)
    }
    fun removeArticle(key: String) {
        sharedPreferencesEditor.remove(key).apply()
    }
    fun isArticleSaved(article: ArticlesItem): Boolean {
        val gson = Gson()
        val json = gson.toJson(article)
        for ((key, value) in sharedPreferences.all) {
            if (key.startsWith("article_")) {
                val articleJson = value as String
                if (articleJson == json) {
                    return true
                }
            }
        }
        return false
    }



}