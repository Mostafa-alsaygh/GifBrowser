package com.example.gifbrowserapp.presentation.features.home

interface HomeInteractionListener {

    fun navigateToSearch()
    fun onClickGif()
    fun navigateToCategoryScreen()
    fun navigateToDetailGif()


    object Preview : HomeInteractionListener {
        override fun navigateToSearch() {}

        override fun onClickGif() {}

        override fun navigateToCategoryScreen() {}

        override fun navigateToDetailGif() {}
    }
}