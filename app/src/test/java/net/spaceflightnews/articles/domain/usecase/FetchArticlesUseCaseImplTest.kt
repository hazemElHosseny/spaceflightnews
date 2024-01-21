package net.spaceflightnews.articles.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import net.spaceflightnews.TestUtils
import net.spaceflightnews.articles.domain.ArticlesRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FetchArticlesUseCaseImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val articlesRepository = mockk<ArticlesRepository>()
    private val useCase = FetchArticlesUseCaseImpl(articlesRepository)

    @Test
    fun `invoke should return expected PagingData`() = runTest {
        val mockArticles = TestUtils.getArticles()
        coEvery { articlesRepository.getArticlesPagingData() } returns flow {
            emit(PagingData.from(mockArticles))
        }
        val resultPagingData = useCase.invoke().asSnapshot()
        assertEquals(mockArticles, resultPagingData)
    }
}
