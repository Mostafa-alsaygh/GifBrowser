package com.example.gifbrowserapp.presentation.features.search

import com.example.gifbrowserapp.data.entities.local.GifItem

data class SearchUiState(
    val gifsData: List<SearchedGif> = emptyList(),
    val originalGifUrl: String = "",
    val webGifUrl: String = "",
    val selectedGif: GifItem? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


data class SearchedGif(
    val id: String = "",
    val url: String = "",
    val title: String = "",
    val images: SearchedGifImages
)

data class SearchedGifImages(
    val original: String = "",
    val fixedWidthDownsampled: String = "",
)
