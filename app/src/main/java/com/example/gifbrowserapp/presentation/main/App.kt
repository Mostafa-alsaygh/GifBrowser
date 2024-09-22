package com.example.gifbrowserapp.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.gifbrowserapp.presentation.components.snack_bar.ObserveAsEvent
import com.example.gifbrowserapp.presentation.components.snack_bar.SnackbarController
import com.example.gifbrowserapp.presentation.design.AppTheme
import com.example.gifbrowserapp.presentation.navigation.GifAppGraph
import com.example.gifbrowserapp.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun App(){

    AppTheme {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        ObserveAsEvent(
            flow = SnackbarController.event,
            snackbarHostState
        ) { event ->
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()

                val result = snackbarHostState.showSnackbar(
                    message = event.message,
                    actionLabel = event.action?.name,
                    duration = SnackbarDuration.Indefinite
                )
                if (result == SnackbarResult.ActionPerformed) {
                    event.action?.action?.invoke()
                }
            }

        }
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            },
            modifier = Modifier.fillMaxSize(),
            contentColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->

            GifAppGraph(
                navController = navController,
                startDestination = Screen.Home,
                modifier = Modifier.padding(innerPadding)
            )

        }
    }
}