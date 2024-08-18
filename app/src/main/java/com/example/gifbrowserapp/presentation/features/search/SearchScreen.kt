package com.example.gifbrowserapp.presentation.features.search

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.components.GifsGrid
import com.example.gifbrowserapp.presentation.components.Loading
import com.example.gifbrowserapp.presentation.components.SearchField
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.navigation.destinations.navigateToGiphyDetailsScreen
import com.example.gifbrowserapp.presentation.navigation.destinations.navigateToHome
import com.example.gifbrowserapp.presentation.utils.extensions.Listen
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import timber.log.Timber

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController,
    categoryName: String?
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle("")
    val uiState: SearchUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent: SearchEvent? by viewModel.uiEvent.collectAsState(null)
    val listener: SearchInteractionListener = viewModel

    LaunchedEffect(categoryName) {
        viewModel.setCategoryName(categoryName ?: emptyString())
    }

    Content(
        state = uiState,
        searchQuery = searchQuery,
        listener = listener,
        viewModel =viewModel
    )

    uiEvent?.Listen { currentEvent ->
        when (currentEvent) {
            SearchEvent.NavigateToGiphyDetailsScreen ->
                navController.navigateToGiphyDetailsScreen(
                    Uri.encode(uiState.originalGifUrl),
                    Uri.encode(uiState.webGifUrl)
                )
            SearchEvent.NavigateBack -> navController.navigateUp()

        }
    }
}


@Composable
fun Content(
    state: SearchUiState,
    searchQuery: String,
    listener: SearchInteractionListener,
    viewModel: SearchViewModel,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchField(
        query = searchQuery.takeIf { it != "{categoryName}" } ?: emptyString(),
        active = true,
        placeholder = "Search For Gifs",
        onQueryChange = listener::onSearchQueryChange,
        onActiveChange = {},
        onClear = {
            listener.onClearSearch()
        },
        onLeadingClick = {
            focusManager.clearFocus()
            listener.navigateBack()
        },
        onSearch = {
            focusManager.clearFocus()
            listener.onSearchQueryChange(searchQuery)
        },
        modifier = Modifier.focusRequester(focusRequester)
    ) {
        if (state.isLoading) {
            Loading(showDialog = true, hintText = stringResource(R.string.please_wait))
        } else {
            GifsGrid(
                gifList = state.gifsData,
                onGifClick = { originalGifUrl, gifWebUrl -> viewModel.onClickGif(originalGifUrl, gifWebUrl) },
                extractOriginalGifUrl = { it.images.original },
                extractDownsampledUrl = { it.images.fixedWidthDownsampled },
                extractGifWebUrl = { it.url }
            )
        }
    }
}

