package com.example.gifbrowserapp.presentation.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.gifbrowserapp.presentation.features.giphyDetails.GiphyDetailsScreen
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.utils.extensions.navigate


fun NavController.navigateToGiphyDetailsScreen(){
    navigate(Screen.GiphyDetails, popBackstack = true)
}

fun NavGraphBuilder.giphyDetailsDestination(navController: NavController){
    composable(Screen.GiphyDetails.route){
        GiphyDetailsScreen(viewModel = hiltViewModel(), navController =navController)
    }
}