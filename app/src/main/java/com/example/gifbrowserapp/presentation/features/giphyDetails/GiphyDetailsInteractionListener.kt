package com.example.gifbrowserapp.presentation.features.giphyDetails

interface GiphyDetailsInteractionListener {


    fun navigateToHome()
    fun onClickCopyLink()
    fun onClickShare()
    fun onClickFavorite()

    object Preview : GiphyDetailsInteractionListener {
        override fun navigateToHome()   {}
        override fun onClickCopyLink()  {}
        override fun onClickShare()     {}
        override fun onClickFavorite()  {}
    }

}