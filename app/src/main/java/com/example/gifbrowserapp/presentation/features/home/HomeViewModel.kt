package com.example.gifbrowserapp.presentation.features.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.remote.mappers.toTrendingGifList
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifEvent
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifState
import com.example.gifbrowserapp.presentation.utils.extensions.toGifItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    private val localGifsRepository: LocalGifsRepository,
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()),
    HomeInteractionListener {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _favoriteGifState = MutableStateFlow(FavoriteGifState())
    val favoriteGifState = _favoriteGifState.asStateFlow()


    init {
        fetchTrendingAndCategoriesGiphy()
        loadFavorites()
    }

    fun onEvent(event: FavoriteGifEvent) {
        when (event) {
            FavoriteGifEvent.LoadFavorites -> loadFavorites()
            is FavoriteGifEvent.AddFavorite -> addFavoriteGif(event.gif)
            is FavoriteGifEvent.RemoveFavorite -> removeFavoriteGif(event.gif)
        }
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
        emitNewEvent(HomeEvent.NavigateToSearchScreen)
    }

    override fun onClickGif(trendingGif: TrendingGif) {
        val gifItem = trendingGif.toGifItem()
        _uiState.value = _uiState.value.copy(selectedGif = gifItem)
        emitNewEvent(HomeEvent.NavigateToGiphyDetailsScreen)
    }

    override fun onClickFavoriteGif(favoriteGif: FavoriteGif) {
        _uiState.value = _uiState.value.copy(selectedGif = favoriteGif.toGifItem())
        emitNewEvent(HomeEvent.NavigateToGiphyDetailsScreen)
    }

    override fun onClickCategory(categoryName: String) {
        _uiState.value.categoryName = categoryName
        emitNewEvent(HomeEvent.NavigateToSearchScreenWithCategoryName)
    }


    private fun loadFavorites() {
        viewModelScope.launch {
            _favoriteGifState.update { it.copy(isLoading = true) }
            try {
                localGifsRepository.getFavoriteGifs().collect { favoriteGifs ->
                    _favoriteGifState.update {
                        it.copy(
                            favoriteGifs = favoriteGifs,
                            isLoading = false
                        )
                    }
                    Log.d("INHOMEScreen", "onLOADFAVORITES: ${favoriteGifState.value.favoriteGifs}")
                }
            } catch (e: Exception) {
                _favoriteGifState.update { it.copy(isLoading = false, error = e.message) }

            }
        }
    }

    private fun addFavoriteGif(gif: FavoriteGif) {
        viewModelScope.launch {
            try {
                localGifsRepository.addFavoriteGif(favoriteGif = gif)
                loadFavorites()
            } catch (e: Exception) {
                _favoriteGifState.update { it.copy(error = e.message) }
            }
        }
    }

    private fun removeFavoriteGif(gif: FavoriteGif) {
        viewModelScope.launch {
            try {
                localGifsRepository.removeFavoriteGif(gif)
                loadFavorites()
            } catch (e: Exception) {
                _favoriteGifState.update { it.copy(error = e.message) }
            }
        }
    }

}