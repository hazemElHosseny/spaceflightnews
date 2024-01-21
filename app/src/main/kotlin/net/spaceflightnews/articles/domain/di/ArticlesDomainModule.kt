package net.spaceflightnews.articles.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.spaceflightnews.articles.domain.usecase.FetchArticlesUseCase
import net.spaceflightnews.articles.domain.usecase.FetchArticlesUseCaseImpl

/**
 * Hilt Module for Domain/Business layer dependencies provider and binds
 *
 */
@Module
@InstallIn(SingletonComponent::class)
interface ArticlesDomainModule {

    /**
     * binds articles usecase interface with its implementation so it can be injected
     *
     * @param usecase the implementation for articles usecase that will be injected
     *
     * @return the interface that other classes will depend on
     *
     */
    @Binds
    fun bindsFetchArticlesUseCase(
        usecase: FetchArticlesUseCaseImpl
    ): FetchArticlesUseCase
}
