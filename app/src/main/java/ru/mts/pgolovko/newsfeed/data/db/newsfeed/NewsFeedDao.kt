package ru.mts.pgolovko.newsfeed.data.db.newsfeed

import androidx.room.*

@Dao
interface NewsFeedDao {

    @Query("SELECT * from ${FeedItemEntity.TABLE_NAME}")
    suspend fun getAll(): List<FeedItemEntity>

    @Transaction
    suspend fun update(entities: List<FeedItemEntity>) {
        clear()
        insert(entities)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<FeedItemEntity>)

    @Query("DELETE FROM ${FeedItemEntity.TABLE_NAME}")
    suspend fun clear()
}