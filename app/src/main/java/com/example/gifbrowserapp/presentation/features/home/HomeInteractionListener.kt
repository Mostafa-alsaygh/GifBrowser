package com.example.gifbrowserapp.presentation.features.home

interface HomeInteractionListener {

    fun navigateToSearch()
    fun onClickGif(gifUrlOriginal: String, gifWebUrl: String)
    fun navigateToCategoryGifsScreen()
    fun navigateToDetailGif()


    object Preview : HomeInteractionListener {
        override fun navigateToSearch() {}
        override fun onClickGif(gifUrlOriginal: String, gifWebUrl: String) {}

        override fun navigateToCategoryGifsScreen() {}
        override fun navigateToDetailGif() {}
    }
}