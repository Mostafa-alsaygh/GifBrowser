package com.example.gifbrowserapp.presentation.features.giphyDetails

import com.example.gifbrowserapp.data.entities.local.GifItem
import com.example.gifbrowserapp.presentation.utils.extensions.emptyGifItem


data class GiphyDetailsUiState(
    val gifItem: GifItem = emptyGifItem(),
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
)


