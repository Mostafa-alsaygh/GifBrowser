package com.example.gifbrowserapp.presentation.features.search

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.components.GifsGrid
import com.example.gifbrowserapp.presentation.components.Loading
import com.example.gifbrowserapp.presentation.components.SearchField
import com.example.gifbrowserapp.presentation.components.SearchGrid
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.utils.extensions.Listen
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString
import com.example.gifbrowserapp.presentation.utils.extensions.painter

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val state: SearchUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle("")
    val event: SearchEvent? by viewModel.event.collectAsState(null)
    val listener: SearchInteractionListener = viewModel

    Content(
        state = state,
        searchQuery = searchQuery,
        listener = listener
    )

    event?.Listen { currentEvent ->
        when (currentEvent) {
            SearchEvent.NavigateBack -> navController.navigateUp()
            SearchEvent.NavigateToDetailGif -> navController.navigate(Screen.GiphyDetails.route)
        }
    }
}

@Composable
fun Content(
    state: SearchUiState,
    searchQuery: String,
    listener: SearchInteractionListener
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchField(
        query = searchQuery,
        active = true,
        placeholder = "Search For Gifs",
        onQueryChange = listener::onSearchQueryChange,
        onActiveChange = {},
        onClear = listener::onClearSearch,
        onLeadingClick = {
            focusManager.clearFocus()
            listener.navigateBack()
        },
        modifier = Modifier.focusRequester(focusRequester)
    ){
        if (state.isLoading) {
            Loading(showDialog = true, hintText = R.string.please_wait.toString())
        } else {
                SearchGrid(gifList = state.gifsData)

        }
    }


}
