package ru.mts.pgolovko.newsfeed.data.db.newsfeed

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FeedItemEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsFeedDao(): NewsFeedDao
}