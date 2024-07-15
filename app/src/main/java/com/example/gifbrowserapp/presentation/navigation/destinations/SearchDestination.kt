package com.example.gifbrowserapp.presentation.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.gifbrowserapp.presentation.features.search.SearchScreen
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.utils.extensions.navigate


fun NavController.navigateToSearch(){
    navigate(Screen.Search, popBackstack = true)

}

fun NavGraphBuilder.searchDestination(navController: NavController) {
    composable(Screen.Search.route) {
        SearchScreen(
            viewModel = hiltViewModel(),
            navController = navController)
    }

}