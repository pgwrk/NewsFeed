package ru.mts.pgolovko.newsfeed.data.repositories

import ru.mts.pgolovko.newsfeed.data.remote.newsfeed.NewsFeedApi
import ru.mts.pgolovko.newsfeed.data.remote.newsfeed.toDomain
import ru.mts.pgolovko.newsfeed.data.utils.Result
import ru.mts.pgolovko.newsfeed.model.FeedItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsFeedRepository @Inject constructor(
    private val newsFeedApi: NewsFeedApi
) {
    suspend fun getFeed(): Result<List<FeedItem>> {
        return try {
            val feed = newsFeedApi.getSampleData().feed.map { it.toDomain() }
            Result.Success(feed)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}