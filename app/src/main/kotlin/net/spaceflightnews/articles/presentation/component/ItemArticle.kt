package net.spaceflightnews.articles.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.spaceflightnews.articles.domain.model.Article
import net.spaceflightnews.common.formatToReadable
import java.time.LocalDateTime

/**
 * UI for article item to be shown in the listing
 * @param article the article we want to show
 *
 */
@Composable
fun ItemArticle(
    article: Article,
) {
    // card layout configuration
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        // column to show article information in vertical orientation
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // text field to show article title
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
            )
            // if we have publishedAt date we will show it under the title and add necessary spacing
            article.publishedAt?.let { publishedAt ->
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    // convert LocalDateTime to user readable format
                    text = publishedAt.formatToReadable(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            // text field to show article summary
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = article.summary,
            )
        }
    }
}

/**
 * preview the article item UI 
 */
@Preview
@Composable
fun PreviewItemArticle() {
    ItemArticle(
        Article(
            title = "title",
            summary = "Summary",
            publishedAt = LocalDateTime.now()
        )
    )
}
