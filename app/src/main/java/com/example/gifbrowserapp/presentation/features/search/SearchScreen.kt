package com.example.gifbrowserapp.presentation.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.components.GifsGrid
import com.example.gifbrowserapp.presentation.components.Loading
import com.example.gifbrowserapp.presentation.components.SearchField
import com.example.gifbrowserapp.presentation.design.Spacer
import com.example.gifbrowserapp.presentation.navigation.destinations.navigateToGiphyDetailsScreen
import com.example.gifbrowserapp.presentation.utils.extensions.Listen
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController,
    categoryName: String?
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle("")
    val uiState: SearchUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent: SearchEvent? by viewModel.event.collectAsState(null)
    val listener: SearchInteractionListener = viewModel

    LaunchedEffect(categoryName) {
        viewModel.setCategoryName(categoryName ?: emptyString())
    }

    Content(
        uiState = uiState,
        searchQuery = searchQuery.takeIf { it != "{categoryName}" } ?: emptyString(),
        listener = listener,
        viewModel = viewModel
    )

    uiEvent?.Listen { currentEvent ->
        when (currentEvent) {
            SearchEvent.NavigateToGiphyDetailsScreen -> {
                uiState.selectedGif?.let { gifItem ->
                    navController.navigateToGiphyDetailsScreen(gifItem)
                }
            }

            SearchEvent.NavigateBack -> navController.navigateUp()
        }
    }
}


@Composable
fun Content(
    uiState: SearchUiState,
    searchQuery: String,
    listener: SearchInteractionListener,
    viewModel: SearchViewModel,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        SearchField(
            query = searchQuery,
            active = true,
            placeholder = "Search For Gifs",
            onQueryChange = listener::onSearchQueryChange,
            onActiveChange = {},
            onClear = { listener.onClearSearch() },
            onLeadingClick = {
                focusManager.clearFocus()
                listener.navigateBack()
            },
            onSearch = {
                focusManager.clearFocus()
                listener.onSearchQueryChange(searchQuery)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        ) {
            Column(Modifier.padding(horizontal = 8.dp)) { // Recent Searches
                if (recentSearches.isNotEmpty() && uiState.gifsData.isEmpty()) {
                    Spacer.Small(vertical = true)

                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        content = {
                            items(recentSearches) { recentSearch ->
                                Text(
                                    text = recentSearch.searchQuery,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .clickable {
                                            listener.onSearchQueryChange(recentSearch.searchQuery)
                                        },
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    )
                }
                if (recentSearches.isEmpty()) {
                    Text(
                        text = "No resent searches available.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                when {
                    uiState.isLoading -> Loading(
                        showDialog = true,
                        hintText = stringResource(R.string.please_wait)
                    )

                    uiState.gifsData.isNotEmpty() -> {
                        GifsGrid(
                            gifList = uiState.gifsData,
                            onGifClick = { viewModel.onClickGif(it) },
                            extractUrl = { it.images.fixedWidthDownsampled }
                        )
                    }

                    else -> {
                        Text(
                            text = if (searchQuery.isEmpty()) {
                                "Search first to find GIFs!${searchQuery}"
                            } else {
                                "No GIFs found for \"${searchQuery}\"."
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

    }

}
