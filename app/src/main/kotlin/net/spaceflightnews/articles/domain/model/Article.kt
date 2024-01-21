package net.spaceflightnews.articles.domain.model

import java.time.LocalDateTime

/**
 * Domain layer model for Articles that will be used for business logic
 *
 */
data class Article(
    val title: String,
    val summary: String,
    val publishedAt: LocalDateTime?
)
