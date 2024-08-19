package com.example.gifbrowserapp.presentation.features.home

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    var tabRowState by remember { mutableIntStateOf(0) }
    val titles = listOf("Trends", "Categories", "Favorites")
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent: HomeEvent? by viewModel.event.collectAsState(null)
    val listener: HomeInteractionListener = viewModel


    uiEvent?.Listen { currentEvent ->
        when (currentEvent) {
            HomeEvent.NavigateToGiphyDetailsScreen -> {
                navController.navigateToGiphyDetailsScreen(
                    Uri.encode(uiState.originalGifUrl),
                    Uri.encode(uiState.webGifUrl)
                )
            }

            HomeEvent.NavigateToSearchScreenWithCategoryName -> navController.navigateToSearch(
                uiState.categoryName
            )

            HomeEvent.NavigateToSearchScreen -> navController.navigateToSearch(emptyString())
        }
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
                onGifClick = { originalGifUrl, webGifUrl ->
                    viewModel.onClickGif(
                        originalGifUrl,
                        webGifUrl
                    )
                },
                extractOriginalGifUrl = { it.images.original },
                extractDownsampledUrl = { it.images.fixedWidthDownsampled },
                extractGifWebUrl = { it.url },
                modifier = Modifier.padding(top = 16.dp)
            )

            1 -> CategoriesGrid(categories = uiState.categories, viewModel)


        }

    }
}
