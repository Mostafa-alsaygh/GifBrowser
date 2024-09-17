package com.example.gifbrowserapp.presentation.components.snack_bar

data class SnackbarAction(
    val name :String,
    val action: suspend () -> Unit
)
