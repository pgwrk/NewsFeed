package ru.mts.pgolovko.newsfeed.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import ru.mts.pgolovko.newsfeed.ui.screens.feed.NewsFeedScreen
import ru.mts.pgolovko.newsfeed.ui.screens.feed.NewsFeedViewModel
import ru.mts.pgolovko.newsfeed.ui.theme.NewsFeedTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsFeedViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsFeedTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    NewsFeedScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsFeedTheme {
        Greeting("Android")
    }
}