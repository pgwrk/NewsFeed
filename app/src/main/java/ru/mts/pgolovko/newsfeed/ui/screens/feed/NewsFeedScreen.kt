package ru.mts.pgolovko.newsfeed.ui.screens.feed

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.mts.pgolovko.newsfeed.R
import ru.mts.pgolovko.newsfeed.model.FeedItem
import ru.mts.pgolovko.newsfeed.ui.screens.feed.view.FeedItemView
import ru.mts.pgolovko.newsfeed.ui.theme.NewsFeedTheme

@Composable
fun NewsFeedScreen(viewModel: NewsFeedViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    NewsFeedScreenContent(uiState) {
        viewModel.refresh()
    }
}

@Composable
fun NewsFeedScreenContent(
    uiState: NewsFeedUiState,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        when (uiState) {
            NewsFeedUiState.Loading -> LoadingScreen(Modifier.align(Alignment.Center))
            is NewsFeedUiState.Error -> ErrorScreen(
                modifier = Modifier.align(Alignment.Center),
                errorDescription = uiState.errorDescription,
                onRetry = onRefresh
            )
            is NewsFeedUiState.Content -> {
                if (uiState.feed.isEmpty()) {
                    EmptyContentScreen(modifier = Modifier.align(Alignment.Center))
                } else {
                    ContentScreen(feed = uiState.feed, onRefresh)
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier.size(16.dp))
}

@Composable
private fun ErrorScreen(
    modifier: Modifier,
    errorDescription: String,
    onRetry: () -> Unit
) {
    Column(modifier.padding(32.dp)) {
        Text(
            text = errorDescription,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedButton(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
private fun ContentScreen(
    feed: List<FeedItem>,
    onRefresh: () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            itemsIndexed(feed, key = { _,item -> item.id }) { index, item ->
                FeedItemView(item)
                if (index != feed.lastIndex) {
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun EmptyContentScreen(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.no_data),
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
    )
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NewsFeedScreenPreview() = NewsFeedTheme {
    NewsFeedScreenContent(
        //NewsFeedUiState.Loading.
        //NewsFeedUiState.Error("No internet connection. Connect your phone to internet and try again"),
        //NewsFeedUiState.Content(listOf()),
        NewsFeedUiState.Content(testFeed),
        onRefresh = {}
    )
}

private val testFeed = listOf(
    FeedItem(
        id = 1,
        title = "8 причин не покупать iPhone 14",
        description = "Apple представила новое поколение iPhone. Базовая модель всегда была самой популярной в линейке: инновации по неизменной цене,..."
    ),
    FeedItem(
        id = 2,
        title = "5 любопытных моментов, которые надо знать про новые iPhone 14 и iPhone 14 Pro",
        description = "Apple представила новые iPhone 14 и iPhone 14 Pro. Обновление получилось достаточно сочным. Есть большой простор для дискуссии."
    ),
    FeedItem(
        id = 3,
        title = "Россияне чаще выбирают цвет Deep Purple при заказе iPhone 14 - Мировые новости",
        description = "Россияне чаще выбирают цвет Deep Purple при заказе нового смартфона iPhone 14. Об этом сообщает ТАСС со ссылкой на данные ритейлеров."
    ),
)