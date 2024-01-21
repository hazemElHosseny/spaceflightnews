package net.spaceflightnews.articles.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.spaceflightnews.articles.domain.ArticlesRepository
import net.spaceflightnews.articles.domain.model.Article
import javax.inject.Inject

/**
 * usecase to fetch articles using articles repository
 */
class FetchArticlesUseCaseImpl @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : FetchArticlesUseCase {
    override fun invoke(): Flow<PagingData<Article>> = articlesRepository.getArticlesPagingData()
}
