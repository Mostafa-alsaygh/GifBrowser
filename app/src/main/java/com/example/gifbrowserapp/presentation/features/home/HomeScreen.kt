package com.example.gifbrowserapp.presentation.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.components.CategoriesGrid
import com.example.gifbrowserapp.presentation.components.GifsGrid
import com.example.gifbrowserapp.presentation.components.SearchField
import com.example.gifbrowserapp.presentation.design.AppTheme
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifEvent
import com.example.gifbrowserapp.presentation.navigation.destinations.navigateToGiphyDetailsScreen
import com.example.gifbrowserapp.presentation.navigation.destinations.navigateToSearch
import com.example.gifbrowserapp.presentation.utils.extensions.Listen
import com.example.gifbrowserapp.presentation.utils.extensions.clickableNoRipple
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString
import com.example.gifbrowserapp.presentation.utils.extensions.painter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent: HomeEvent? by viewModel.event.collectAsState(null)
    val favoriteGifsState by viewModel.favoriteGifState.collectAsState()
    val listener: HomeInteractionListener = viewModel

    var tabRowState by remember { mutableIntStateOf(0) }
    val titles = listOf("Trends", "Categories", "Favorites")

    uiEvent?.Listen { currentEvent ->
        when (currentEvent) {
            HomeEvent.NavigateToGiphyDetailsScreen -> {
                uiState.selectedGif?.let { gifItem ->
                    navController.navigateToGiphyDetailsScreen(gifItem)
                }
            }

            HomeEvent.NavigateToSearchScreenWithCategoryName -> navController.navigateToSearch(
                uiState.categoryName
            )

            HomeEvent.NavigateToSearchScreen -> navController.navigateToSearch(emptyString())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(FavoriteGifEvent.LoadFavorites)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SearchField(
            query = "",
            active = false,
            enabled = false,
            placeholder = "Search For Gifs",
            onQueryChange = {},
            onClear = {},
            leadingIcon = R.drawable.ic_search.painter,
            onActiveChange = {},
            onSearch = {},
            modifier = Modifier.clickableNoRipple(onClick = listener::navigateToSearch)
        ) {}

        PrimaryTabRow(
            selectedTabIndex = tabRowState,
            containerColor = AppTheme.colors.surface,
            contentColor = AppTheme.colors.primary
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = tabRowState == index,
                    onClick = { tabRowState = index },
                    text = {
                        Text(
                            text = title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }

        when (tabRowState) {
            0 -> GifsGrid(
                gifList = uiState.gifsData,
                onGifClick = { trendingGif ->
                    viewModel.onClickGif(trendingGif)
                },
                extractUrl = { it.images.fixedWidthDownsampled },
                modifier = Modifier.padding(top = 16.dp)
            )

            1 -> CategoriesGrid(categories = uiState.categories, viewModel)

            2 -> GifsGrid(
                gifList = favoriteGifsState.favoriteGifs,
                onGifClick = { gifItem -> viewModel.onClickFavoriteGif(gifItem) },
                extractUrl = { favoriteGif ->
                    favoriteGif.originalGifUrl
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}