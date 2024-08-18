package com.example.gifbrowserapp.presentation.features.home

import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.remote.mappers.toTrendingGifList
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    private val favoriteGifRepository: LocalGifsRepository
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()),
    HomeInteractionListener {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    private val _uiEvent = MutableSharedFlow<HomeEvent>()
    val uiEvent = _uiEvent.asSharedFlow()



    init {
        fetchTrendingAndCategoriesGiphy()
    }

    private fun fetchTrendingAndCategoriesGiphy() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val gifs = giphyRepository.takeTrendingGifs()
                val categories = giphyRepository.takeCategoriesOfGiphy()
                _uiState.value = HomeUiState(
                    gifsData = gifs.data.toTrendingGifList(),
                    categories = categories.data,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = HomeUiState(isLoading = false, errorMessage = e.message)
            }
        }
    }

    override fun navigateToSearch() {
        viewModelScope.launch {
            _uiEvent.emit(HomeEvent.NavigateToSearchScreen)
        }
    }

    override fun onClickGif(originalGifUrl: String, webGifUrl: String) {
        viewModelScope.launch {
            _uiState.value.originalGifUrl = originalGifUrl
            _uiState.value.webGifUrl = webGifUrl
            _uiEvent.emit(HomeEvent.NavigateToGiphyDetailsScreen)
        }

    }

    override fun onClickCategory(categoryName: String) {
        viewModelScope.launch {
            _uiState.value.categoryName = categoryName
            _uiEvent.emit(HomeEvent.NavigateToSearchScreenWithCategoryName)
        }
    }

}