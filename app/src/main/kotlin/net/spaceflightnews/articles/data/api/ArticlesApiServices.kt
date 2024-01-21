package net.spaceflightnews.articles.data.api

import net.spaceflightnews.articles.data.model.ArticleResponse
import net.spaceflightnews.network.model.PagingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api services for articles
 * any endpoint related to articles feature should go here
 *
 */
internal interface ArticlesApiServices {

    /**
     *  articles listing endpoint
     *  @param limit number of articles to receive in the response
     *  @param offset offset can be use to request next page of articles
     *  by passing page number multiple by limit
     *
     *  @return list of articles with limit sizes
     *  and paging information(next page url and previous page url)
     *
     */
    @GET(value = "articles")
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PagingResponse<ArticleResponse>
}
