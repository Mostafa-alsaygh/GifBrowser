package com.example.gifbrowserapp.presentation.features.localGiphy

import com.example.gifbrowserapp.data.entities.local.FavoriteGif


sealed interface FavoriteGifEvent {
    object LoadFavorites : FavoriteGifEvent
    data class AddFavorite(val gif: FavoriteGif) : FavoriteGifEvent
    data class RemoveFavorite(val gif: FavoriteGif) : FavoriteGifEvent
}