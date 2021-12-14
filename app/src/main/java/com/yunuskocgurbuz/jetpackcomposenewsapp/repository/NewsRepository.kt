package com.yunuskocgurbuz.jetpackcomposenewsapp.repository

import com.yunuskocgurbuz.jetpackcomposenewsapp.model.NewsList
import com.yunuskocgurbuz.jetpackcomposenewsapp.service.NewsAPI
import com.yunuskocgurbuz.jetpackcomposenewsapp.util.Constants.API_KEY
import com.yunuskocgurbuz.jetpackcomposenewsapp.util.Constants.SOURCES
import com.yunuskocgurbuz.jetpackcomposenewsapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class NewsRepository @Inject constructor(
    private val api : NewsAPI
){
    suspend fun getNewsList() : Resource<NewsList>{
        val response = try {
            api.getNewsList(SOURCES, API_KEY)

        }catch (e: Exception){

            return Resource.Error("Error!")
        }

        return Resource.Success(response)
    }
}