package ru.mts.pgolovko.newsfeed.data.remote.newsfeed

import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsFeedApi {
    @GET("api/v1/getfeed")
    @Headers("Content-Type:application/json; charset=utf-8;")
    suspend fun getSampleData(): FeedNewsDto
}