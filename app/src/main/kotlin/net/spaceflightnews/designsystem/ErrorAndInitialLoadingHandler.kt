package net.spaceflightnews.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import net.spaceflightnews.R

/**
 * handle initial loading status(full screen loading)
 * and handle errors by showing snack bar
 * @param loadState combined loading states that combines different loading steps
 * @param snackBarHostState the host state will be used to show snack bar for error
 * @param paddingValues padding for other views
 * @param onRetryClicked call back for retry button shown in snack bar
 */
@Composable
fun ErrorAndInitialLoadingHandler(
    loadState: CombinedLoadStates,
    snackBarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
    onRetryClicked: () -> Unit
) {
    when {
        loadState.refresh is LoadState.Loading -> {
            LoadingFullScreen(modifier = Modifier.padding(paddingValues))
        }

        loadState.refresh is LoadState.Error ||
                loadState.append is LoadState.Error -> {
            val error = (loadState.refresh as? LoadState.Error)
                ?: (loadState.append as? LoadState.Error)
            val retryText = stringResource(id = R.string.retry)
            LaunchedEffect(snackBarHostState) {
                snackBarHostState
                    .showSnackbar(
                        message = error?.error?.localizedMessage.orEmpty(),
                        actionLabel = retryText,
                        duration = SnackbarDuration.Indefinite
                    )
                    .let { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            onRetryClicked()
                        }
                    }
            }
        }
    }
}

@Preview
@Composable
fun PreviewShowInitialLoading() {
    val refresh = LoadState.Loading
    val prepend = LoadState.NotLoading(false)
    val append = LoadState.NotLoading(false)
    val combinedLoadStates = CombinedLoadStates(
        refresh = refresh,
        prepend = prepend,
        append = append,
        source = LoadStates(refresh, prepend, append)
    )
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            SpaceflightNewsTopAppBar(title = stringResource(id = R.string.app_name))
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        ErrorAndInitialLoadingHandler(
            loadState = combinedLoadStates,
            snackBarHostState = snackBarHostState,
            paddingValues = paddingValues
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewShowErrorInitial() {
    val refresh = LoadState.Error(Throwable("can't load data"))
    val prepend = LoadState.NotLoading(false)
    val append = LoadState.NotLoading(false)
    val combinedLoadStates = CombinedLoadStates(
        refresh = refresh,
        prepend = prepend,
        append = append,
        source = LoadStates(refresh, prepend, append)
    )
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            SpaceflightNewsTopAppBar(title = stringResource(id = R.string.app_name))
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        ErrorAndInitialLoadingHandler(
            loadState = combinedLoadStates,
            snackBarHostState = snackBarHostState,
            paddingValues = paddingValues
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewShowErrorWhileLoadingPages() {
    val refresh = LoadState.NotLoading(false)
    val prepend = LoadState.NotLoading(false)
    val append = LoadState.Error(Throwable("can't load data"))
    val combinedLoadStates = CombinedLoadStates(
        refresh = refresh,
        prepend = prepend,
        append = append,
        source = LoadStates(refresh, prepend, append)
    )
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            SpaceflightNewsTopAppBar(title = stringResource(id = R.string.app_name))
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        ErrorAndInitialLoadingHandler(
            loadState = combinedLoadStates,
            snackBarHostState = snackBarHostState,
            paddingValues = paddingValues
        ) {

        }
    }
}
