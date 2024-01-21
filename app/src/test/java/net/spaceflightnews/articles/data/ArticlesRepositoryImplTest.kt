package net.spaceflightnews.articles.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.testing.asSnapshot
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.spaceflightnews.TestUtils
import net.spaceflightnews.articles.data.api.ArticlesApiServices
import net.spaceflightnews.articles.data.model.ArticleResponse
import net.spaceflightnews.articles.domain.model.Article
import net.spaceflightnews.common.Mapper
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ArticlesRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val apiServices = mockk<ArticlesApiServices>()
    private val mapper = mockk<Mapper<ArticleResponse, Article>>()
    private val repository = ArticlesRepositoryImpl(apiServices, mapper)

    @Test
    fun `getArticlesPagingData should return expected PagingData`() = runTest {
        coEvery { apiServices.getArticles(any(), any()) } returns TestUtils.getArticlesPagingResponseWithOutNextPage()
        val mockArticles = TestUtils.getArticles()
        coEvery { mapper.map(any()) } answers { mockArticles.find { it.title == firstArg<ArticleResponse>().title }!! }
        val pagingDataFlow = repository.getArticlesPagingData()
        assertEquals(mockArticles, pagingDataFlow.asSnapshot())
    }
}
