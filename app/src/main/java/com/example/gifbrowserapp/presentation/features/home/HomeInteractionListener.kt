package com.example.gifbrowserapp.presentation.features.home

interface HomeInteractionListener {

    fun navigateToSearch()
    fun onClickGif()
    fun onClickTrendingTab()
    fun onClickCategoriesTab()
    fun navigateToCategoryScreen()
    fun navigateToDetailGif()


    object Preview : HomeInteractionListener {
        override fun navigateToSearch() {}

        override fun onClickGif() {}

        override fun onClickTrendingTab() {}

        override fun onClickCategoriesTab() {}

        override fun navigateToCategoryScreen() {}

        override fun navigateToDetailGif() {}
    }
}