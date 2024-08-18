package com.example.gifbrowserapp.presentation.features.home

interface HomeInteractionListener {

    fun navigateToSearch()
    fun onClickGif(originalGifUrl: String, webGifUrl: String)
    fun onClickCategory(categoryName: String)

    object Preview : HomeInteractionListener {
        override fun navigateToSearch() {}
        override fun onClickGif(originalGifUrl: String, webGifUrl: String) {}
        override fun onClickCategory(categoryName: String) {}
    }
}