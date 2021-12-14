package com.yunuskocgurbuz.jetpackcomposenewsapp.dependencyinjection

import com.yunuskocgurbuz.jetpackcomposenewsapp.repository.NewsRepository
import com.yunuskocgurbuz.jetpackcomposenewsapp.service.NewsAPI
import com.yunuskocgurbuz.jetpackcomposenewsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        api: NewsAPI
    ) = NewsRepository(api)

    @Singleton
    @Provides
    fun provideNewsApi(): NewsAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsAPI::class.java)
    }


}