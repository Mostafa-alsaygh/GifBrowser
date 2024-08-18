package com.example.gifbrowserapp.presentation.features.localGiphy

sealed interface FavoriteGifEvent {
    object MakeGifFavorite : FavoriteGifEvent
    data class UnFavoriteGif(val id: Int) : FavoriteGifEvent
    data class SetID(val id: Int) : FavoriteGifEvent
    data class SetUrl(val url: String) : FavoriteGifEvent
    data class SetWebUrl(val webUrl: String) : FavoriteGifEvent

}