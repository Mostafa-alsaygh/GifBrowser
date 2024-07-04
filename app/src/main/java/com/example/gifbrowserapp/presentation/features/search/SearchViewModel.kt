package com.example.gifbrowserapp.presentation.features.search

import androidx.lifecycle.SavedStateHandle
import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchUiState, SearchEvent>(SearchUiState()), SearchInteractionListener {
    override fun navigateBack() {
        TODO("Not yet implemented")
    }

    override fun onClearSearch() {
        TODO("Not yet implemented")
    }

    override fun navigateToGifDetails() {
        TODO("Not yet implemented")
    }

    override fun onSearchQueryChange(value: String) {
        TODO("Not yet implemented")
    }

    override fun onSelectGif(place: GifData) {
        TODO("Not yet implemented")
    }


}