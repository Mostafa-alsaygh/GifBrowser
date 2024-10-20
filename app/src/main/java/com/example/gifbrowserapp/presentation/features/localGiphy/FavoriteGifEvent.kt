package com.example.gifbrowserapp.presentation.features.localGiphy



sealed interface FavoriteGifEvent {
    object LoadFavorites : FavoriteGifEvent
}