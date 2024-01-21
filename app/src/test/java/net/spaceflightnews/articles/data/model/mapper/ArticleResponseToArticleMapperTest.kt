package net.spaceflightnews.articles.data.model.mapper

import io.mockk.every
import io.mockk.mockkStatic
import net.spaceflightnews.TestUtils
import net.spaceflightnews.common.serverDateTimeWithToDate
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleResponseToArticleMapperTest {
    @Test
    fun `map ArticleResponse to Article`() {
        mockkStatic("net.spaceflightnews.common.DateExtensionsKt") {
            every { any<String>().serverDateTimeWithToDate() } returns TestUtils.publishedAtForArticle1
            val mapper = ArticleResponseToArticleMapper()
            val result = mapper.map(TestUtils.articleResponse1)
            assertEquals(TestUtils.articleResponse1.title, result.title)
            assertEquals(TestUtils.articleResponse1.summary, result.summary)
            assertEquals(TestUtils.publishedAtForArticle1, result.publishedAt)
        }
    }
}