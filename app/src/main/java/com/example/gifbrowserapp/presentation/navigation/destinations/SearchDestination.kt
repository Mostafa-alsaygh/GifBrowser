package com.example.gifbrowserapp.presentation.navigation.destinations

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gifbrowserapp.presentation.features.search.SearchScreen
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString


fun NavController.navigateToSearch(categoryName: String) {
    if (categoryName == emptyString()) navigate(Screen.Search.route)
    else navigate(Screen.Search.createRoute(categoryName))
}

fun NavGraphBuilder.searchDestination(navController: NavController) {
    composable(Screen.Search.route,
        arguments = listOf(
            navArgument("categoryName") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        SearchScreen(
            viewModel = hiltViewModel(),
            navController = navController,
            categoryName = backStackEntry.arguments?.getString("categoryName")
        )

    }

}