package com.example.gifbrowserapp.presentation.features.giphyDetails

interface GiphyDetailsInteractionListener {


    fun onClickShare()
    fun onClickFavorite()

    object Preview : GiphyDetailsInteractionListener {
        override fun onClickShare()     {}
        override fun onClickFavorite()  {}
    }

}