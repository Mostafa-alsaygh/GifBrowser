package com.example.gifbrowserapp.presentation.features.search


interface SearchInteractionListener {
    fun navigateBack()
    fun onClearSearch()
    fun onSearchQueryChange(value: String)
    fun onClickGif(originalGifUrl: String, webGifUrl: String)

    object Preview : SearchInteractionListener {
        override fun navigateBack() {}
        override fun onClearSearch() {}
        override fun onSearchQueryChange(value: String) {}
        override fun onClickGif(originalGifUrl: String, webGifUrl: String) {}
    }
}
