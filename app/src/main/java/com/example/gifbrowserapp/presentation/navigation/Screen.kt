package com.example.gifbrowserapp.presentation.navigation

open class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Search : Screen("search_screen/{categoryName}") {
        fun createRoute(categoryName: String) = "search_screen/$categoryName"
    }

    data object GiphyDetails : Screen("giphy_details_screen/{originalGifUrl}/{webGifUrl}") {
        fun createRoute(originalGifUrl: String, webGifUrl: String) =
            "giphy_details_screen/$originalGifUrl/$webGifUrl"
    }

}