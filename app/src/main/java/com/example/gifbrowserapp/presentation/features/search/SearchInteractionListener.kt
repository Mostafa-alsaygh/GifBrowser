package com.example.gifbrowserapp.presentation.features.search

import com.example.gifbrowserapp.data.entities.gifData.GifData


interface SearchInteractionListener {
    fun navigateBack()
    fun onClearSearch()
    fun navigateToGifDetails()
    fun onSearchQueryChange(value: String)
    fun onSelectGif(data: GifData)

    object Preview : SearchInteractionListener {
        override fun navigateBack() {}
        override fun onClearSearch() {}
        override fun navigateToGifDetails() {}
        override fun onSearchQueryChange(value: String) {}
        override fun onSelectGif(data: GifData) {}
    }
}
