package com.example.gifbrowserapp.presentation.features.home

import com.example.gifbrowserapp.data.entities.categories.CategoryData
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString

data class HomeUiState(
    val gifsData: List<TrendingGif> = emptyList(),
    val categories:List<CategoryData> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    var gifUrlOriginal: String = emptyString(),
    var gifWebUrl: String= emptyString(),

    )


data class TrendingGif(
    val id: String = "",
    val url: String = "",
    val title: String = "",
    val images: GifImages
)

data class GifImages(
    val original: String = "",
    val fixedWidthDownsampled: String = "",
)
