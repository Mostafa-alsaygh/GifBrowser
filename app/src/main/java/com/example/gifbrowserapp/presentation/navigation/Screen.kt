package com.example.gifbrowserapp.presentation.navigation

open class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Search : Screen("search_screen")
    data object GiphyDetails : Screen("giphy_details_screen/{gifId}"){
        fun createRoute(gifId: String) = "giphy_details_screen/$gifId"
    }

}