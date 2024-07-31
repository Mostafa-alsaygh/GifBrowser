package com.example.gifbrowserapp.presentation.features.home

import androidx.navigation.NavController

interface HomeInteractionListener {

    fun navigateToSearch(navController: NavController)
    fun onClickGif(gifUrlOriginal: String, gifUrl: String, navController: NavController)
    fun navigateToCategoryGifsScreen()
    fun navigateToDetailGif()


    object Preview : HomeInteractionListener {
        override fun navigateToSearch(navController: NavController) {}
        override fun onClickGif(gifUrlOriginal: String, gifUrl: String, navController: NavController) {}
        override fun navigateToCategoryGifsScreen() {}
        override fun navigateToDetailGif() {}
    }
}