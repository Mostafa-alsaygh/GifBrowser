package com.example.gifbrowserapp.presentation.features.localGiphy

import com.example.gifbrowserapp.data.entities.local.FavoriteGif

data class FavoriteGifState(
    val favoriteGifs: List<FavoriteGif> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
