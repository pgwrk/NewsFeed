package ru.mts.pgolovko.newsfeed.ui.screens.feed

import ru.mts.pgolovko.newsfeed.model.FeedItem

sealed class NewsFeedUiState {
    object Loading: NewsFeedUiState()
    class Error(val errorDescription: String): NewsFeedUiState()
    class Content(val feed: List<FeedItem>): NewsFeedUiState()
}