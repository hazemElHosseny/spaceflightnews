package net.spaceflightnews.articles.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.spaceflightnews.articles.data.api.ArticlesApiServices
import net.spaceflightnews.articles.data.pagingsource.ArticlesPagingSource
import net.spaceflightnews.articles.data.model.ArticleResponse
import net.spaceflightnews.articles.domain.ArticlesRepository
import net.spaceflightnews.articles.domain.model.Article
import net.spaceflightnews.common.Mapper
import javax.inject.Inject

/**
 * Articles repository responsible of getting the data from data layer and
 * prepare it (do any mapping needed) to be used in domain/business layer
 *
 */
internal class ArticlesRepositoryImpl @Inject constructor(
    private val apiServices: ArticlesApiServices,
    private val mapper: Mapper<ArticleResponse, Article>
) : ArticlesRepository {

    /**
     * get articles from data layer and convert model to business model
     * configure pager for business needs
     *
     */
    override fun getArticlesPagingData(): Flow<PagingData<Article>> {
        return Pager( // pager to handle paging
            PagingConfig( // paging configuration
                pageSize = PAGE_SIZE, // number of items per page
                initialLoadSize = PAGE_SIZE // initial page size
            )
        ) { // articles paging source factory
            ArticlesPagingSource(apiServices)
        }.flow.map { articleResponseData -> // use flow, and then map model to domain model
            articleResponseData.map { articleResponse ->
                mapper.map(articleResponse)
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
