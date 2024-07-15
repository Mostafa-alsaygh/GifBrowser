package com.example.gifbrowserapp.presentation.features.giphyDetails

import com.example.gifbrowserapp.data.entities.Original
import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.features.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GiphyDetailsViewModel @Inject constructor(
) : BaseViewModel<GiphyDetailsUiState, GiphyDetailsEvent>(GiphyDetailsUiState()),
    GiphyDetailsInteractionListener {

    private val _gifDetails = MutableStateFlow(value = Original())
    val gifDetails: StateFlow<Original> = _gifDetails

    override fun navigateToHome() {
        TODO("Not yet implemented")
    }

    override fun onClickCopyLink() {
        TODO("Not yet implemented")
    }

    override fun onClickShare() {
        TODO("Not yet implemented")
    }

    override fun onClickFavorite() {
        TODO("Not yet implemented")
    }
}