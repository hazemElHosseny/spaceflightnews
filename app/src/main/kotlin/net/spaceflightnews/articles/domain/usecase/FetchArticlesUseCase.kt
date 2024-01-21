package net.spaceflightnews.articles.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.spaceflightnews.articles.domain.model.Article

/**
 * usecase to fetch articles
 */
interface FetchArticlesUseCase {
    operator fun invoke(): Flow<PagingData<Article>>
}
