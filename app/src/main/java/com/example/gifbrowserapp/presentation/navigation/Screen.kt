package com.example.gifbrowserapp.presentation.navigation

open class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Search : Screen("search_screen")
    data object GiphyDetails : Screen("chat_support_screen")

}