package com.example.gifbrowserapp.presentation.features.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.components.GifsGrid
import com.example.gifbrowserapp.presentation.components.SearchField
import com.example.gifbrowserapp.presentation.design.AppTheme.sizes
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString
import com.example.gifbrowserapp.presentation.utils.extensions.painter

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavController
) {
    val state: SearchUiState by viewModel.state.collectAsStateWithLifecycle()
    val event: SearchEvent? by viewModel.event.collectAsState(null)
    val listener: SearchInteractionListener = viewModel

    Content(
        state = state,
        listener = listener
    )


}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Content(
    state: SearchUiState = SearchUiState(),
    listener: SearchInteractionListener = SearchInteractionListener.Preview
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val searchQuery by state.searchQuery.collectAsStateWithLifecycle(emptyString())


    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    SearchField(
        query = searchQuery,
        active = true,
        placeholder = "Search For Gifs",
        onQueryChange = listener::onSearchQueryChange,
        onActiveChange = {},
        onClear = listener::onClearSearch,
        leadingIcon = R.drawable.ic_search.painter,
        onLeadingClick = {
            focusManager.clearFocus()
            listener.navigateBack()
        },
        modifier = Modifier.focusRequester(focusRequester)
    ) {
        GifsGrid(gifList = state.gifData)
        //TODO (check the data path)
    }

//    Loading(showDialog = state.isLoading, hintText = R.string.please_wait.string)
}