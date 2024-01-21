package net.spaceflightnews.articles.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.spaceflightnews.articles.data.api.ArticlesApiServices
import net.spaceflightnews.articles.data.model.ArticleResponse

/**
 * responsible of loading pages from endpoint, it serve as datasource
 */
internal class ArticlesPagingSource(
    private val apiServices: ArticlesApiServices
) : PagingSource<Int, ArticleResponse>() {

    /**
     * in case of refresh needed this function is responsible of
     * returning the page number that need to be refreshed
     * @Param state is PagingState of the currently fetched data
     * which includes the most recently accessed position in the list via PagingState.anchorPosition.
     * @return the page number that needs to be fetched
     *
     */
    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        // get anchor position which is the most recently accessed item in the list
        // then we try to get closest page to anchor position
        // from the page we can get the prev/next key and then get current page key.
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    /**
     * load function is responsible of loading pages
     * @param params which contains load params that are pasted from pager
     * and can be configured by PagingConfig
     *
     * @return LoadResult which is the result of the loaded page,
     * can be error for example or a page that contain prev and next key and the current page list
     *
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        return runCatching {
            val currentPage = params.key ?: 0
            val articles = apiServices.getArticles(
                limit = params.loadSize,
                offset = currentPage * params.loadSize
            )
            LoadResult.Page(
                data = articles.results,
                prevKey = articles.previous?.let {
                    currentPage.dec()
                },
                nextKey = articles.next?.let {
                    currentPage.inc()
                }
            )
        }.getOrElse { exception ->
            LoadResult.Error(exception)
        }
    }
}
