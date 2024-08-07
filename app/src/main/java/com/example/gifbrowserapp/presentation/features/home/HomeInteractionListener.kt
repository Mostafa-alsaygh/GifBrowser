package com.example.gifbrowserapp.presentation.features.home

interface HomeInteractionListener {

    fun navigateToSearch()
    fun navigateToCategoryGifsScreen()
    fun navigateToDetailGif()
    fun onClickGif(gifUrlOriginal: String, gifWebUrl: String)
    fun onClickCategory(categoryName: String)

    object Preview : HomeInteractionListener {
        override fun navigateToSearch() {}
        override fun navigateToCategoryGifsScreen() {}
        override fun navigateToDetailGif() {}
        override fun onClickGif(gifUrlOriginal: String, gifWebUrl: String) {}
        override fun onClickCategory(categoryName: String) {}
    }
}