package com.example.gifbrowserapp.presentation.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gifbrowserapp.presentation.features.giphyDetails.GiphyDetailsScreen
import com.example.gifbrowserapp.presentation.navigation.Screen


fun NavController.navigateToGiphyDetailsScreen(gifId: String) {
    navigate(Screen.GiphyDetails.createRoute(gifId))
}

fun NavGraphBuilder.giphyDetailsDestination(navController: NavController) {
    composable(
        Screen.GiphyDetails.route,
        arguments = listOf(navArgument("gifId") { type = NavType.StringType })
    ) { backStackEntry ->
        GiphyDetailsScreen(
            viewModel = hiltViewModel(),
            navController = navController,
            gifId = backStackEntry.arguments?.getString("gifId")
        )
    }
}
