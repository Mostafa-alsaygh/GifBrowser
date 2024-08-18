package com.example.gifbrowserapp.presentation.features.localGiphy

import com.example.gifbrowserapp.data.entities.local.FavoriteGif

data class FavoriteGifState(
    val favoriteGifs : List<FavoriteGif> = emptyList(),
    val id : Int = 0,
    val url : String = "",
    val webUrl : String = "",
    val isFavorite : Boolean = false
)