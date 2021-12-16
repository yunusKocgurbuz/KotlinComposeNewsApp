package com.yunuskocgurbuz.jetpackcomposenewsapp.view


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.yunuskocgurbuz.jetpackcomposenewsapp.model.Article
import com.yunuskocgurbuz.jetpackcomposenewsapp.uiview.StaggeredVerticalGrid
import com.yunuskocgurbuz.jetpackcomposenewsapp.viewmodel.NewsListViewModel


@Composable
fun NewsListScreen(
    navController: NavController,
    viewModel: NewsListViewModel = hiltViewModel()
){

    Surface(
        color = Color.Gray,
        modifier = Modifier.fillMaxSize()
    ){
        Column{
            
            AppTitle(title = "News App")

            SearchBar(hint = "Search...", modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                viewModel.searchNewsList(it)
            }

            NewsList(navController = navController)
           // StaggeredGrid()
        }

    }

}

@Composable
fun AppTitle(title: String){
    Text(text = title , modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        textAlign = TextAlign.Left,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
){
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplaye by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier){
        BasicTextField(value = text, onValueChange = {
            text = it
            onSearch(it)
        }, maxLines = 1, singleLine = true, textStyle = TextStyle.Default, modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .onFocusChanged {
                isHintDisplaye = it.isFocused != true && text.isEmpty()
            } )

        if(isHintDisplaye){
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))

        }
    }

}

@Composable
fun NewsList(navController: NavController, viewModel: NewsListViewModel = hiltViewModel() ){
    val cryptoList by remember {viewModel.newsList}
    val errorMessage by remember {viewModel.errorMessage}
    val isLoading by remember {viewModel.isLoading}
    NewsListView(cryptoList, navController = navController)


    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()) {
        if(isLoading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                viewModel.loadNews()
            }
        }
    }

}

@Composable
fun NewsListView(newses: List<Article>, navController: NavController){


    StaggeredGrid(navController, newses = newses)

    /*
    LazyColumn(contentPadding = PaddingValues(0.dp)){
        items(newses){news ->
            NewsRow(navController = navController, news = news)
        }
    }

     */



}


@Composable
fun NewsRow(navController: NavController, news: Article){

    val newsurl: String = news.url
    val newsImageurl:String = news.urlToImage

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable {
                    navController.navigate(
                        "news_detail_screen/{${news.author}}/{${news.title}}/{${news.description}}/{${news.url}}/{${newsurl.toString()}}/{${newsImageurl.toString()}}/{${news.content}}"
                    )
                },
            backgroundColor = MaterialTheme.colors.secondary,
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ) {

            Column() {
                Image(painter = rememberImagePainter(data = news.urlToImage),
                    contentDescription = news.author,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp, 200.dp)
                        .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp)),
                    contentScale = ContentScale.FillHeight
                )
                Text(text = news.title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(2.dp),
                    fontSize = 15.sp,
                    color = Color.Black)


                Row() {
                    Text(text = "Author: ",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(1.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black)

                    Text(text = news.author,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(1.dp),
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.primaryVariant)
                }


                Text(text = "  "+news.publishedAt,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(1.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black)

            }
        }


}

@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
){
    Column() {
        Text(error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onRetry }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}


@Composable
fun StaggeredGrid(navController: NavController, newses: List<Article>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {




        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(5.dp)
        ) {
            StaggeredVerticalGrid(
                numColumns = 2, //put the how many column you want
                modifier = Modifier.padding(5.dp)
            ) {

                //this is

                for(news in newses){
                    NewsRow(navController = navController, news = news)
                }
            }
        }
    }
}


