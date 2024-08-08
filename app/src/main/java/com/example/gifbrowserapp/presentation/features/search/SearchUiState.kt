package com.example.gifbrowserapp.presentation.features.search

import com.example.gifbrowserapp.presentation.utils.extensions.emptyString

data class SearchUiState(
    val gifsData: List<SearchedGif> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    var originalGifUrl: String = emptyString(),
    var webGifUrl: String = emptyString(),
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
