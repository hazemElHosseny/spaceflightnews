package net.spaceflightnews.articles.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import net.spaceflightnews.R
import net.spaceflightnews.articles.domain.model.Article
import net.spaceflightnews.articles.presentation.component.ItemArticle
import net.spaceflightnews.designsystem.ErrorAndInitialLoadingHandler
import net.spaceflightnews.designsystem.LoadingItem
import net.spaceflightnews.designsystem.SpaceflightNewsTopAppBar

/**
 * Full Article screen UI
 * @param viewModel injected by hilt that will be used to get articles list that need to be shown
 *
 */
@Composable
fun ArticlesScreen(
    viewModel: ArticlesViewModel = hiltViewModel()
) {
    // collect articles as lazy paging items
    val articlePagingItems: LazyPagingItems<Article> = viewModel.articles.collectAsLazyPagingItems()
    // host state for snack bar, the snack bar is used to show errors
    val snackBarHostState = remember { SnackbarHostState() }
    // Material Design layout, that support using several material design component
    Scaffold(
        topBar = {
            // spaceflight news app top bar
            SpaceflightNewsTopAppBar(title = stringResource(id = R.string.app_name))
        },
        snackbarHost = {
            // snack bar host that will be used later to show snack bar
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->

        // composable function that handles initial loading and all errors
        ErrorAndInitialLoadingHandler(
            loadState = articlePagingItems.loadState,
            snackBarHostState = snackBarHostState,
            paddingValues = paddingValues
        ) {
            articlePagingItems.retry()
        }

        // showing articles list
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            // top spacing for item space symmetry
            item { Spacer(modifier = Modifier.padding(4.dp)) }
            // show article items
            items(articlePagingItems.itemCount) { index ->
                articlePagingItems[index]?.let { article ->
                    ItemArticle(article = article)
                }
            }
            // if we are trying to load another page add a loader in the bottom on the list
            if (articlePagingItems.loadState.append == LoadState.Loading) {
                item { LoadingItem() }
            }
            // bottom spacing for item space symmetry
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }
    }
}
