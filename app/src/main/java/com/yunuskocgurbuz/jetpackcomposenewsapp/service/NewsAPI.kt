package com.yunuskocgurbuz.jetpackcomposenewsapp.service

import com.yunuskocgurbuz.jetpackcomposenewsapp.model.NewsList
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getNewsList(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String,

    ) : NewsList
}