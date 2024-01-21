package net.spaceflightnews.articles.data.model.mapper

import net.spaceflightnews.articles.data.model.ArticleResponse
import net.spaceflightnews.articles.domain.model.Article
import net.spaceflightnews.common.Mapper
import net.spaceflightnews.common.serverDateTimeWithToDate
import javax.inject.Inject

/**
 * mapper that maps ArticleResponse model to Article
 *
 */
internal class ArticleResponseToArticleMapper @Inject constructor() :
    Mapper<ArticleResponse, Article> {

    /**
     * mapping function that return domain model of the Article
     *
     * @param from is the ArticleResponse that we want to map it to Article
     * @return Article model
     */
    override fun map(from: ArticleResponse) = Article(
        title = from.title,
        summary = from.summary,
        // convert string date to LocalDateTime that can be used in business logic
        publishedAt = from.publishedAt.serverDateTimeWithToDate(),
    )
}
