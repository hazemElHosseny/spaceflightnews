package net.spaceflightnews.articles.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data layer model for Articles that we receive from API
 *
 * Kotlinx Serialization is use to serialize/deserialize model
 *  all fields must have SerialName to avoid fields serialize/deserialize issues
 *  that can be caused by fields renaming when using obfuscation(ProGuard)
 *
 */
@Serializable
internal data class ArticleResponse(
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("published_at")
    val publishedAt: String
)
