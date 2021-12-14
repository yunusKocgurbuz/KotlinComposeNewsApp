package com.yunuskocgurbuz.jetpackcomposenewsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp : Application()
{
    override fun onCreate() {
        super.onCreate()
    }
}