package net.spaceflightnews.articles.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import net.spaceflightnews.articles.domain.usecase.FetchArticlesUseCaseImpl
import javax.inject.Inject

/**
 * article view model that is responsible of serving articles to UI by invoking usecase
 * @param articlesUseCase the usecase which return articles
 *
 */
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    articlesUseCase: FetchArticlesUseCaseImpl
) : ViewModel() {
    // invoke article usecase, use caching in viewModelScope to avoid multiple collect
    // and convert it to lazily stateflow
    val articles = articlesUseCase.invoke().cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )
}
