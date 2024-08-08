package com.example.gifbrowserapp.presentation.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gifbrowserapp.presentation.features.giphyDetails.GiphyDetailsScreen
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.utils.extensions.navigate


fun NavController.navigateToGiphyDetailsScreen(gifUrlOriginal: String, gifUrl: String) {
    navigate(Screen.GiphyDetails.createRoute(gifUrlOriginal, gifUrl))
}

fun NavGraphBuilder.giphyDetailsDestination(navController: NavController) {
    composable(
        Screen.GiphyDetails.route,
        arguments = listOf(
            navArgument("originalGifUrl") { type = NavType.StringType },
            navArgument("webGifUrl") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        GiphyDetailsScreen(
            viewModel = hiltViewModel(),
            navController = navController,
            gifUrlOriginal = backStackEntry.arguments?.getString("originalGifUrl"),
            gifUrl = backStackEntry.arguments?.getString("webGifUrl")

        )
    }
}
