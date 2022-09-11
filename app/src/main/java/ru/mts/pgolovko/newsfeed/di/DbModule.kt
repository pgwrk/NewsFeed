package ru.mts.pgolovko.newsfeed.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mts.pgolovko.newsfeed.data.db.newsfeed.AppDatabase
import ru.mts.pgolovko.newsfeed.data.db.newsfeed.NewsFeedDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNewsFeedDao(database: AppDatabase): NewsFeedDao = database.newsFeedDao()
}