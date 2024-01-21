package net.spaceflightnews.articles.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.spaceflightnews.articles.data.ArticlesRepositoryImpl
import net.spaceflightnews.articles.data.api.ArticlesApiServices
import net.spaceflightnews.articles.data.model.ArticleResponse
import net.spaceflightnews.articles.data.model.mapper.ArticleResponseToArticleMapper
import net.spaceflightnews.articles.domain.ArticlesRepository
import net.spaceflightnews.articles.domain.model.Article
import net.spaceflightnews.common.Mapper
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Hilt Module for Data layer dependencies provider and binds
 *
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface ArticlesDataModule {

    /**
     * binds articles repository interface with its implementation so it can be injected
     *
     * @param repository the implementation for articles repository that will be injected
     *
     * @return the interface that other classes will depend on
     *
     */
    @Binds
    fun bindsArticlesRepository(
        repository: ArticlesRepositoryImpl
    ): ArticlesRepository

    /**
     * binds mapper that map article response received from APT to article (Domain/Business model)
     *
     * @param mapper the implementation for article response mapper that will be injected
     *
     * @return the interface that other classes will depend on
     *
     */
    @Binds
    fun bindArticleResponseToArticleMapper(
        mapper: ArticleResponseToArticleMapper
    ): Mapper<ArticleResponse, Article>

    companion object {
        /**
         * Provider fuction that provide the article api service
         * that can be injected in any datasource/pagingsource
         *
         * @param retrofit the retrofit configuration that will be use to create API service
         * @return ArticlesApiServices that can be use to call the articles API
         *
         */
        @Singleton
        @Provides
        fun provideArticlesApiServices(
            retrofit: Retrofit
        ): ArticlesApiServices {
            return retrofit.create(ArticlesApiServices::class.java)
        }
    }
}
