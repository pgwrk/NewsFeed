package ru.mts.pgolovko.newsfeed.data.repositories

import ru.mts.pgolovko.newsfeed.data.db.newsfeed.FeedItemEntity
import ru.mts.pgolovko.newsfeed.data.db.newsfeed.NewsFeedDao
import ru.mts.pgolovko.newsfeed.data.db.newsfeed.toDomain
import ru.mts.pgolovko.newsfeed.data.remote.newsfeed.NewsFeedApi
import ru.mts.pgolovko.newsfeed.data.remote.newsfeed.toDomain
import ru.mts.pgolovko.newsfeed.data.utils.Result
import ru.mts.pgolovko.newsfeed.model.FeedItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsFeedRepository @Inject constructor(
    private val newsFeedDao: NewsFeedDao,
    private val newsFeedApi: NewsFeedApi
) {
    suspend fun get(getCached: Boolean): Result<List<FeedItem>> {
        val cached = if (getCached) {
            getFromDb()
        } else {
            listOf()
        }

        return if (cached.isNotEmpty()) {
            Result.Success(cached)
        } else {
            try {
                val feed = getRemote()
                updateDb(feed)
                Result.Success(feed)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    private suspend fun getFromDb(): List<FeedItem> = newsFeedDao.getAll().map { it.toDomain() }

    private suspend fun getRemote(): List<FeedItem> = newsFeedApi.getSampleData().feed.shuffled().mapIndexed { id, dto -> dto.toDomain(id) }

    private suspend fun updateDb(feed: List<FeedItem>) = newsFeedDao.update(feed.map { it.toEntity() })
}

private fun FeedItem.toEntity() = FeedItemEntity(id, title, description)