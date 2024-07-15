package com.example.gifbrowserapp.presentation.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.gifbrowserapp.presentation.features.home.HomeScreen
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.utils.extensions.navigate


fun NavController.navigateToHome() {
    navigate(screen = Screen.Home, popBackstack = true)
}

fun NavGraphBuilder.homeDestination(navController: NavController) {
    composable(Screen.Home.route) {
        HomeScreen(
            viewModel = hiltViewModel(),
            navController = navController
        )
    }
}