package ru.mts.pgolovko.newsfeed.data.db.newsfeed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mts.pgolovko.newsfeed.model.FeedItem

@Entity(tableName = FeedItemEntity.TABLE_NAME)
class FeedItemEntity(
    @PrimaryKey
    @ColumnInfo(name = FN_ID)
    val id: Int,

    @ColumnInfo(name = FN_TITLE)
    val title: String,

    @ColumnInfo(name = FN_DESCRIPTION)
    val description: String
) {
    companion object {
        const val TABLE_NAME = "news_feed"
        const val FN_ID = "id"
        const val FN_TITLE = "title"
        const val FN_DESCRIPTION = "description"
    }
}

fun FeedItemEntity.toDomain() = FeedItem(id, title, description)