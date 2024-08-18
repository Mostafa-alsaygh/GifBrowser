package com.example.gifbrowserapp.presentation.features.giphyDetails

import com.example.gifbrowserapp.data.entities.remote.Original

data class GiphyDetailsUiState(
    val gifData: Original = Original(),
    val isLoading: Boolean = false
)
