package net.spaceflightnews.articles.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.turbineScope
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.spaceflightnews.TestUtils
import net.spaceflightnews.articles.domain.usecase.FetchArticlesUseCaseImpl
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `articles LiveData should be updated with expected PagingData`() = runTest {
        turbineScope {
            val articlesUseCase = mockk<FetchArticlesUseCaseImpl>()
            val mockArticles = TestUtils.getArticles()
            coEvery { articlesUseCase.invoke() } returns flow {
                emit(PagingData.from(mockArticles))
            }
            val viewModel = ArticlesViewModel(articlesUseCase)
            val articlesPagingData = viewModel.articles.testIn(backgroundScope)
            val resultPagingData = flow {
                emit(articlesPagingData.awaitItem()) // initial empty state
                emit(articlesPagingData.awaitItem()) // real data emitted
            }.asSnapshot()
            Assert.assertEquals(mockArticles, resultPagingData)
        }
    }
}
