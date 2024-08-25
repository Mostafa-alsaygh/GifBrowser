package com.example.gifbrowserapp.presentation.features.giphyDetails

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.entities.local.GifItem
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifState
import com.example.gifbrowserapp.presentation.utils.extensions.toFavoriteGif
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyDetailsViewModel @Inject constructor(
    private val localGifsRepository: LocalGifsRepository,
) : BaseViewModel<GiphyDetailsUiState, GiphyDetailsEvent>(GiphyDetailsUiState()),
    GiphyDetailsInteractionListener {

    private val _uiState = MutableStateFlow(GiphyDetailsUiState())
    val uiState: StateFlow<GiphyDetailsUiState> get() = _uiState

    private val _favoriteGifState = MutableStateFlow(FavoriteGifState())
    val favoriteGifState = _favoriteGifState.asStateFlow()

    private val _gifItem = MutableStateFlow<GifItem?>(null)
    val gifItem = _gifItem.asStateFlow()

    fun setGifItem(gifItem: GifItem) {
        _gifItem.value = gifItem
    }

    override fun navigateToHome() {
        TODO("Not yet implemented")
    }

    override fun onClickShare() {
        TODO("Not yet implemented")
    }

    override fun onClickFavorite() {
        Log.d("INDETAILSScreen", "onClickFavorite:${_gifItem.value}")
        _gifItem.value?.let {
            viewModelScope.launch {
                localGifsRepository.addFavoriteGif(it.toFavoriteGif())
            }
        }
    }
}
