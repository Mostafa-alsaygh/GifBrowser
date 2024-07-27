package com.example.gifbrowserapp.presentation.features.search

data class SearchUiState(
    val gifsData: List<SearchedGif> = emptyList(),
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
