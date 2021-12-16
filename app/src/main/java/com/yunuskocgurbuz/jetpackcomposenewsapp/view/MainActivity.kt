package com.yunuskocgurbuz.jetpackcomposenewsapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yunuskocgurbuz.jetpackcomposenewsapp.ui.theme.JetpackComposeNewsAppTheme
import com.yunuskocgurbuz.jetpackcomposenewsapp.view.NewsDetailScreen
import com.yunuskocgurbuz.jetpackcomposenewsapp.view.NewsListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeNewsAppTheme {
                val navController = rememberNavController()

                NavHost(navController =  navController, startDestination = "news_list_screen"){

                    composable("news_list_screen"){
                        //NewsListScreen
                        NewsListScreen(navController = navController)
                    }

                    composable("news_detail_screen/{author}/{title}/{description}/{url}/{urlToImage}/{publishedAt}/{content}", arguments = listOf(
                        navArgument("author"){
                            type = NavType.StringType
                        },
                        navArgument("title"){
                            type = NavType.StringType
                        },
                        navArgument("description"){
                            type = NavType.StringType
                        },
                        navArgument("url"){
                            type = NavType.StringType
                        },
                        navArgument("urlToImage"){
                            type = NavType.StringType
                        },
                        navArgument("publishedAt"){
                            type = NavType.StringType
                        },
                        navArgument("content"){
                            type = NavType.StringType
                        }
                    )){
                        val author = remember{
                            it.arguments?.getString("author")
                        }
                        val title = remember{
                            it.arguments?.getString("title")
                        }
                        val description = remember{
                            it.arguments?.getString("description")
                        }
                        val url = remember{
                            it.arguments?.getString("url")
                        }
                        val urlToImage = remember{
                            it.arguments?.getString("urlToImage")
                        }
                        val publishedAt = remember{
                            it.arguments?.getString("publishedAt")
                        }
                        val content = remember{
                            it.arguments?.getString("content")
                        }

                        //NewsDetailScreen
                        NewsDetailScreen(
                            author = author ?: "",
                            title = title ?: "",
                            description = description ?: "",
                            url = url ?: "",
                            urlToImage = urlToImage ?: "",
                            publishedAt = publishedAt ?: "",
                            content = content ?: "",
                            navController = navController
                        )
                    }

                }

            }
        }
    }
}
