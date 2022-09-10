package ru.mts.pgolovko.newsfeed.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mts.pgolovko.newsfeed.data.remote.newsfeed.NewsFeedApi
import ru.mts.pgolovko.newsfeed.data.utils.NewsFeedMockInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder().addInterceptor(NewsFeedMockInterceptor()).build()
        return Retrofit.Builder()
            .baseUrl("https://www.mts.ru/newsfeed/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsFeedApi(retrofit: Retrofit): NewsFeedApi = retrofit.create(NewsFeedApi::class.java)
}