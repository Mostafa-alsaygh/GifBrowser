package com.example.gifbrowserapp.presentation.navigation.destinations

import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gifbrowserapp.data.entities.local.GifItem
import com.example.gifbrowserapp.presentation.features.giphyDetails.GiphyDetailsScreen
import com.example.gifbrowserapp.presentation.navigation.Screen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun NavController.navigateToGiphyDetailsScreen(gifItem: GifItem) {
    val gifJson = Uri.encode(Json.encodeToString(gifItem))
    navigate(Screen.GiphyDetails.createRoute(gifJson))
}

fun NavGraphBuilder.giphyDetailsDestination(navController: NavController) {
    composable(
        Screen.GiphyDetails.route,
        arguments = listOf(
            navArgument("gifItemJson") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val gifItemJson = backStackEntry.arguments?.getString("gifItemJson")
        val gifItem = gifItemJson?.let { Json.decodeFromString<GifItem>(it) }

        GiphyDetailsScreen(
            viewModel = hiltViewModel(),
            navController = navController,
            gifItemSent = gifItem

        )
    }
}
