package com.example.gifbrowserapp.presentation.components.snack_bar

data class SnackbarEvent(
    val message :String,
    val action: SnackbarAction? = null,
)
