package com.example.gifbrowserapp.presentation.features.home

import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.remote.mappers.toTrendingGifList
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()),
    HomeInteractionListener {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState


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
                    isLoading = false,


                )
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