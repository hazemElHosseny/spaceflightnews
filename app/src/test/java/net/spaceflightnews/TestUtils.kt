package net.spaceflightnews

import net.spaceflightnews.articles.data.model.ArticleResponse
import net.spaceflightnews.articles.domain.model.Article
import net.spaceflightnews.network.model.PagingResponse
import java.time.LocalDateTime

internal object TestUtils {

    val publishedAtForArticle1: LocalDateTime = LocalDateTime.of(2024, 1, 18, 20, 31, 44)

    val articleResponse1 = ArticleResponse(
        title = "Article 1",
        summary = "Summary 1",
        publishedAt = "2024-01-18T20:31:44Z"
    )
    private val articleResponse2 = ArticleResponse(
        title = "Article 2",
        summary = "Summary 2",
        publishedAt = "2024-01-18T18:39:17Z"
    )

    private val article1 = Article(
        title = "Article 1",
        summary = "Summary 1",
        publishedAt = publishedAtForArticle1
    )
    private val article2 = Article(
        title = "Article 2",
        summary = "Summary 2",
        publishedAt = LocalDateTime.of(2024, 1, 18, 18, 39, 17)
    )

    fun getArticlesPagingResponse() = PagingResponse(
        results = listOf(
            articleResponse1,
            articleResponse2
        ),
        previous = null,
        next = "https://api.spaceflightnewsapi.net/v4/articles/?limit=2&offset=2"
    )

    fun getArticlesPagingResponseWithOutNextPage() = getArticlesPagingResponse().copy(next = null)

    fun getArticlesPagingResponseAsSecondPage() = getArticlesPagingResponse()
        .copy(
            next = "https://api.spaceflightnewsapi.net/v4/articles/?limit=2&offset=4",
            previous = "https://api.spaceflightnewsapi.net/v4/articles/?limit=2&offset=0"
        )

    fun getArticles() = listOf(article1, article2)
}