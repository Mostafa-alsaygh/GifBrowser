package com.example.gifbrowserapp.presentation.features.giphyDetails

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.entities.local.GifItem
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.utils.extensions.toFavoriteGif
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyDetailsViewModel @Inject constructor(
    private val localGifsRepository: LocalGifsRepository,
) : BaseViewModel<GiphyDetailsUiState, GiphyDetailsEvent>(GiphyDetailsUiState()),
    GiphyDetailsInteractionListener {

    private val _uiState = MutableStateFlow(GiphyDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _gifItem = MutableStateFlow<GifItem?>(null)
    val gifItem = _gifItem.asStateFlow()

    fun setGifItem(gifItem: GifItem) {
        _gifItem.value = gifItem
        checkIfFavorite(gifItem)
    }

    private fun createShareIntent(gifUrl: String): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this awesome GIF! $gifUrl")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    private fun checkIfFavorite(gifItem: GifItem) {
        viewModelScope.launch {
            localGifsRepository.getFavoriteGifs().collect { favoriteGifList ->
                val isFavorite = favoriteGifList.any { it.id == gifItem.id }
                _uiState.value = _uiState.value.copy(isFavorite = isFavorite)
            }
        }
    }

    override fun onClickFavorite() {
        _gifItem.value?.let { gifItem ->
            viewModelScope.launch {
                if (_uiState.value.isFavorite) {
                    localGifsRepository.removeFavoriteGif(gifItem.toFavoriteGif())
                    _uiState.value = _uiState.value.copy(isFavorite = false)
                } else {
                    localGifsRepository.addFavoriteGif(gifItem.toFavoriteGif())
                    _uiState.value = _uiState.value.copy(isFavorite = true)
                }
            }
        }
    }

    override fun onClickShare() {
        _gifItem.value?.let { gifItem ->
            val shareIntent = createShareIntent(gifItem.images.original)
            emitNewEvent(GiphyDetailsEvent.ShareGif(shareIntent))
        }
    }
}
