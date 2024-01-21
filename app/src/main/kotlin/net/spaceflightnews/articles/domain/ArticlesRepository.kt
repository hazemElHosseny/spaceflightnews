package net.spaceflightnews.articles.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.spaceflightnews.articles.domain.model.Article

interface ArticlesRepository {
    fun getArticlesPagingData(): Flow<PagingData<Article>>
}
