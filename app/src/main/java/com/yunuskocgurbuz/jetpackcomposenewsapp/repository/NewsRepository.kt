package com.yunuskocgurbuz.jetpackcomposenewsapp.repository

import com.yunuskocgurbuz.jetpackcomposenewsapp.model.NewsList
import com.yunuskocgurbuz.jetpackcomposenewsapp.service.NewsAPI
import com.yunuskocgurbuz.jetpackcomposenewsapp.util.Constants.API_KEY
import com.yunuskocgurbuz.jetpackcomposenewsapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class NewsRepository @Inject constructor(
    private val api : NewsAPI
){
    suspend fun getNewsList() : Resource<NewsList>{
        val response = try {
            api.getNewsList(API_KEY)

        }catch (e: Exception){

            return Resource.Error("Error Message!")
        }


        return Resource.Success(response)
    }


}