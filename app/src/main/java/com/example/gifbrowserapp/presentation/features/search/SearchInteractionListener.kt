package com.example.gifbrowserapp.presentation.features.search


interface SearchInteractionListener {
    fun navigateBack()
    fun onClearSearch()
    fun onSearchQueryChange(value: String)
    fun onClickGif(searchedGif: SearchedGif)

    object Preview : SearchInteractionListener {
        override fun navigateBack() {}
        override fun onClearSearch() {}
        override fun onSearchQueryChange(value: String) {}
        override fun onClickGif(searchedGif: SearchedGif) {}
    }
}
