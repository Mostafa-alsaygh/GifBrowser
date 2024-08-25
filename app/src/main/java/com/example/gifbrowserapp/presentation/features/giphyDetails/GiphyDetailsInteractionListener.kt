package com.example.gifbrowserapp.presentation.features.giphyDetails

interface GiphyDetailsInteractionListener {


    fun navigateToHome()
    fun onClickShare()
    fun onClickFavorite()

    object Preview : GiphyDetailsInteractionListener {
        override fun navigateToHome()   {}
        override fun onClickShare()     {}
        override fun onClickFavorite()  {}
    }

}