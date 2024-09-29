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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyDetailsViewModel @Inject constructor(
    private val localGifsRepository: LocalGifsRepository,
) : BaseViewModel<GiphyDetailsUiState, GiphyDetailsEvent>(GiphyDetailsUiState()),
    GiphyDetailsInteractionListener {

    private val _uiState = MutableStateFlow(GiphyDetailsUiState())
    val uiState = _uiState.asStateFlow()


    fun setGifItem(gifItem: GifItem) {
        _uiState.value = _uiState.value.copy(gifItem = gifItem)
    }

    fun isGifFavoriteFlow(gifItem: GifItem?) =
        localGifsRepository.getFavoriteGifs().map { favoriteGifList ->
            favoriteGifList.any { it.id == gifItem?.id }
        }

    override fun onClickFavorite() {
        val gifItem = _uiState.value.gifItem
        gifItem.let {
            viewModelScope.launch {
                if (_uiState.value.isFavorite) {
                    localGifsRepository.removeFavoriteGif(it.toFavoriteGif())
                    _uiState.value = _uiState.value.copy(isFavorite = false)
                } else {
                    localGifsRepository.addFavoriteGif(it.toFavoriteGif())
                    _uiState.value = _uiState.value.copy(isFavorite = true)
                }
            }
        }
    }

    override fun onClickShare() {
        val gifItem = _uiState.value.gifItem
        gifItem.let {
            val shareIntent = createShareIntent(it.images.original)
            emitNewEvent(GiphyDetailsEvent.ShareGif(shareIntent))
        }
    }

    private fun createShareIntent(gifUrl: String): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this awesome GIF! $gifUrl")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}

