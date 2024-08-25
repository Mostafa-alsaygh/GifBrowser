package com.example.gifbrowserapp.presentation.features.home

import com.example.gifbrowserapp.data.entities.local.FavoriteGif

interface HomeInteractionListener {

    fun navigateToSearch()
    fun onClickGif(trendingGif: TrendingGif)
    fun onClickFavoriteGif(favoriteGif: FavoriteGif)
    fun onClickCategory(categoryName: String)

    object Preview : HomeInteractionListener {
        override fun navigateToSearch() {}
        override fun onClickGif(trendingGif: TrendingGif) {}
        override fun onClickFavoriteGif(favoriteGif: FavoriteGif) {}
        override fun onClickCategory(categoryName: String) {}
    }
}