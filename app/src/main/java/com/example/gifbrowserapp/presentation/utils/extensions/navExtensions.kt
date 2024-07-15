package com.example.gifbrowserapp.presentation.utils.extensions

import androidx.navigation.NavController
import com.example.gifbrowserapp.presentation.navigation.Screen

fun NavController.navigate(
    screen: Screen,
    popBackstack: Boolean = false
) {
    navigate(
        route = screen.route,
        builder = {
            if (popBackstack)
                popUpTo(0) {
                    inclusive = true
                }
            launchSingleTop = true
        }
    )
}