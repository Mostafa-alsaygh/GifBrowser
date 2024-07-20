package com.example.gifbrowserapp.presentation.features.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()),
    HomeInteractionListener {

//    private val _trendingGifs = MutableStateFlow<List<TrendingGif>>(emptyList())
//    val trendingGifs: StateFlow<List<TrendingGif>> = _trendingGifs

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState


    init {
        fetchTrendingGifs()
    }

    private fun fetchTrendingGifs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val gifs = giphyRepository.takeTrendingGifs()
                _uiState.value = HomeUiState(gifsData = gifs.data.toTrendingGifList(), isLoading = false)
                Log.d("MOTAFA",_uiState.value.gifsData.toString())
            } catch (e: Exception) {
                _uiState.value = HomeUiState(isLoading = false, errorMessage = e.message)
            }
        }
    }


    override fun navigateToSearch() {
        TODO("Not yet implemented")
    }

    override fun onClickGif() {
        TODO("Not yet implemented")
    }

    override fun onClickTrendingTab() {
        TODO("Not yet implemented")
    }

    override fun onClickCategoriesTab() {
        TODO("Not yet implemented")
    }

    override fun navigateToCategoryScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToDetailGif() {
        TODO("Not yet implemented")
    }
}