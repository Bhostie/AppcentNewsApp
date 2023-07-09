package com.appcentnewsapp.barisgokmen

import android.app.Application
import android.content.Context

class AppcentNewsApplication : Application(){

    companion object {
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}