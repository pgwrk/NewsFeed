package ru.mts.pgolovko.newsfeed.data.remote.newsfeed

import com.google.gson.annotations.SerializedName
import ru.mts.pgolovko.newsfeed.model.FeedItem

class FeedNewsDto(
    @SerializedName("feed")
    val feed: List<FeedItemDto>
)

class FeedItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
)

fun FeedItemDto.toDomain() = FeedItem(id, title, description)