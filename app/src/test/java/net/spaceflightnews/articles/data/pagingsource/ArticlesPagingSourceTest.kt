package net.spaceflightnews.articles.data.pagingsource

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.spaceflightnews.TestUtils
import net.spaceflightnews.articles.data.api.ArticlesApiServices
import net.spaceflightnews.articles.data.model.ArticleResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticlesPagingSourceTest {
    private val apiServices = mockk<ArticlesApiServices>()
    private val pageSize = 2
    private val pagingSource = ArticlesPagingSource(apiServices)

    @Test
    fun `load success should return LoadResult Page data`() = runTest {
        val mockArticles = TestUtils.getArticlesPagingResponse()
        coEvery { apiServices.getArticles(any(), any()) } returns mockArticles
        val params = PagingSource.LoadParams.Refresh(0, pageSize, false)
        val result = pagingSource.load(params)
        assertEquals(
            PagingSource.LoadResult.Page(
                data = mockArticles.results,
                prevKey = null,
                nextKey = 1
            ),
            result
        )
    }

    @Test
    fun `load success should return LoadResult for second Page data`() = runTest {
        val mockArticles = TestUtils.getArticlesPagingResponseAsSecondPage()
        coEvery { apiServices.getArticles(any(), any()) } returns mockArticles
        val params = PagingSource.LoadParams.Refresh(1, pageSize, false)
        val result = pagingSource.load(params)
        assertEquals(
            PagingSource.LoadResult.Page(
                data = mockArticles.results,
                prevKey = 0,
                nextKey = 2
            ),
            result
        )
    }

    @Test
    fun `get refresh key should return first page when the anchor position is on the first page`() =
        runTest {
            val mockArticlesPage1 = TestUtils.getArticlesPagingResponse().results
            val mockArticlesPage2 = TestUtils.getArticlesPagingResponseAsSecondPage().results
            val configuration = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize
            )
            val states = PagingState(
                pages = listOf(
                    PagingSource.LoadResult.Page(
                        mockArticlesPage1,
                        nextKey = 1,
                        prevKey = null
                    ),
                    PagingSource.LoadResult.Page(
                        mockArticlesPage2,
                        nextKey = 2,
                        prevKey = 0
                    )
                ),
                anchorPosition = 1,
                config = configuration,
                leadingPlaceholderCount = 0
            )
            val result = pagingSource.getRefreshKey(states)
            assertEquals(
                0,
                result
            )
        }

    @Test
    fun `get refresh key should return null when the anchor position is on the first page with only one page`() =
        runTest {
            val mockArticlesPage1 = TestUtils.getArticlesPagingResponse().results
            val configuration = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize
            )
            val states = PagingState(
                pages = listOf(
                    PagingSource.LoadResult.Page<Int, ArticleResponse>(
                        mockArticlesPage1,
                        nextKey = null,
                        prevKey = null
                    )
                ),
                anchorPosition = 1,
                config = configuration,
                leadingPlaceholderCount = 0
            )
            val result = pagingSource.getRefreshKey(states)
            assertEquals(
                null,
                result
            )
        }

    @Test
    fun `get refresh key should return null when the anchor position is null`() =
        runTest {
            val mockArticlesPage1 = TestUtils.getArticlesPagingResponse().results
            val mockArticlesPage2 = TestUtils.getArticlesPagingResponseAsSecondPage().results
            val configuration = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize
            )
            val states = PagingState(
                pages = listOf(
                    PagingSource.LoadResult.Page(
                        mockArticlesPage1,
                        nextKey = 1,
                        prevKey = null
                    ),
                    PagingSource.LoadResult.Page(
                        mockArticlesPage2,
                        nextKey = 2,
                        prevKey = 0
                    )
                ),
                anchorPosition = null,
                config = configuration,
                leadingPlaceholderCount = 0
            )
            val result = pagingSource.getRefreshKey(states)
            assertEquals(
                null,
                result
            )
        }

    @Test
    fun `get refresh key should return second page when the anchor position is on the second page`() =
        runTest {
            val mockArticlesPage1 = TestUtils.getArticlesPagingResponse().results
            val mockArticlesPage2 = TestUtils.getArticlesPagingResponseAsSecondPage().results
            val configuration = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize
            )
            val states = PagingState(
                pages = listOf(
                    PagingSource.LoadResult.Page(
                        mockArticlesPage1,
                        nextKey = 1,
                        prevKey = null
                    ),
                    PagingSource.LoadResult.Page(
                        mockArticlesPage2,
                        nextKey = 2,
                        prevKey = 0
                    )
                ),
                anchorPosition = 3,
                config = configuration,
                leadingPlaceholderCount = 0
            )
            val result = pagingSource.getRefreshKey(states)
            assertEquals(
                1,
                result
            )
        }

    @Test
    fun `load failure should return LoadResult Error`() = runTest {
        val exception = Exception("Mock API error")
        coEvery { apiServices.getArticles(any(), any()) } throws exception
        val params = PagingSource.LoadParams.Refresh(0, pageSize, false)
        val result = pagingSource.load(params)
        assertEquals(
            PagingSource.LoadResult.Error<Int, ArticleResponse>(exception),
            result
        )
    }
}
