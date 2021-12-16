package com.yunuskocgurbuz.jetpackcomposenewsapp.service

import com.yunuskocgurbuz.jetpackcomposenewsapp.model.NewsList
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines?sources=techcrunch")
    suspend fun getNewsList(
        @Query("apiKey") apiKey: String,

    ) : NewsList

}