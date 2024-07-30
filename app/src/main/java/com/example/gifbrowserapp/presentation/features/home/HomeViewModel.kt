package com.example.gifbrowserapp.presentation.features.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.gifbrowserapp.data.remote.mappers.toTrendingGifList
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.navigation.Screen
import com.example.gifbrowserapp.presentation.navigation.destinations.navigateToGiphyDetailsScreen
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


    override fun navigateToSearch(navController: NavController) {
        navController.navigate(Screen.Search.route)
    }

    override fun onClickGif(gifId: String, navController: NavController) {
        navController.navigateToGiphyDetailsScreen(gifId)
        Log.d("INHOMEVIEWMODEL", gifId)

    }

    override fun navigateToDetailGif() {
        TODO("Not yet implemented")
    }

    override fun navigateToCategoryGifsScreen() {
        TODO("Not yet implemented")
    }


}