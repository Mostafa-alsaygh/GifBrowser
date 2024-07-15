package com.example.gifbrowserapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.gifbrowserapp.presentation.navigation.destinations.giphyDetailsDestination
import com.example.gifbrowserapp.presentation.navigation.destinations.homeDestination
import com.example.gifbrowserapp.presentation.navigation.destinations.searchDestination

@Composable
fun GifAppGraph(
    navController: NavHostController,
    startDestination: Screen,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        homeDestination(navController)
        searchDestination(navController)
        giphyDetailsDestination(navController)



    }

}