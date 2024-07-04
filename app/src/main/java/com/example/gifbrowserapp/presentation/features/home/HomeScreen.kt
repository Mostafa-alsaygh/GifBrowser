package com.example.gifbrowserapp.presentation.features.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.components.SearchField
import com.example.gifbrowserapp.presentation.design.AppTheme
import com.example.gifbrowserapp.presentation.utils.extensions.clickableNoRipple
import com.example.gifbrowserapp.presentation.utils.extensions.painter
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("Trends", "Categories")

    val data =viewModel.trendingGifs.collectAsState()

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
            modifier = Modifier.clickableNoRipple(onClick = {})
        ) {}


        PrimaryTabRow(
            selectedTabIndex = state,
            containerColor = AppTheme.colors.surface,
            contentColor = AppTheme.colors.primary
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
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
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Text tab ${state + 1} selected",
            style = AppTheme.typography.body
        )


    }
}


//@Composable
//@Preview(showBackground = true)
//private fun Preview() = HomeScreen(viewModel = , navController = NavController())

