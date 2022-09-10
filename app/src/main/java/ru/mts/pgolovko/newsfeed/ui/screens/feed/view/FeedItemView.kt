package ru.mts.pgolovko.newsfeed.ui.screens.feed.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mts.pgolovko.newsfeed.model.FeedItem
import ru.mts.pgolovko.newsfeed.ui.theme.NewsFeedTheme

@Composable
fun FeedItemView(
    item: FeedItem
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FeedItemViewPreview() = NewsFeedTheme {
    FeedItemView(
        FeedItem(
            id = 1,
            title = "5 любопытных моментов, которые надо знать про новые iPhone 14 и iPhone 14 Pro",
            description = "Apple представила новые iPhone 14 и iPhone 14 Pro. Обновление получилось достаточно сочным. Есть большой простор для дискуссии."
        )
    )
}