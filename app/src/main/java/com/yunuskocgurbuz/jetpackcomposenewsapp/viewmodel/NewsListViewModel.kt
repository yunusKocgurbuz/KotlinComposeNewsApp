package com.yunuskocgurbuz.jetpackcomposenewsapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuskocgurbuz.jetpackcomposenewsapp.model.Article
import com.yunuskocgurbuz.jetpackcomposenewsapp.model.NewsList
import com.yunuskocgurbuz.jetpackcomposenewsapp.repository.NewsRepository
import com.yunuskocgurbuz.jetpackcomposenewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    var newsList = mutableStateOf<List<Article>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialNewsList = listOf<Article>()
    private var isSearchStarting = true

    init {
        loadNews()
    }

    fun searchNewsList(query : String){
        val listToSearch = if(isSearchStarting){
            newsList.value
        }else{
            initialNewsList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                newsList.value = initialNewsList
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
                it.title.contains(query.trim(), ignoreCase = true)
            }

            if(isSearchStarting){
                initialNewsList = newsList.value
                isSearchStarting = false
            }

            newsList.value = results

        }
    }

    fun loadNews(){

        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getNewsList()

            when(result){
                is Resource.Success -> {
                    val newsItems = result.data!!.articles

                    errorMessage.value = ""
                    isLoading.value = false
                    newsList.value += newsItems

                }

                is Resource.Error -> {

                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }

        }

    }





}