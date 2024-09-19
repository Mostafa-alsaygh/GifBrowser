package com.example.gifbrowserapp.presentation.features.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.remote.mappers.toTrendingGifList
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.data.utils.NetworkMonitor
import com.example.gifbrowserapp.presentation.components.snack_bar.SnackbarAction
import com.example.gifbrowserapp.presentation.components.snack_bar.SnackbarController
import com.example.gifbrowserapp.presentation.components.snack_bar.SnackbarEvent
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifEvent
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifState
import com.example.gifbrowserapp.presentation.utils.extensions.toGifItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val giphyRepository: GiphyRepository,
    private val localGifsRepository: LocalGifsRepository,
    private val networkMonitor: NetworkMonitor
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()),
    HomeInteractionListener {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _favoriteGifState = MutableStateFlow(FavoriteGifState())
    val favoriteGifState = _favoriteGifState.asStateFlow()

    init {
        fetchTrendingAndCategoriesGiphy()
        loadFavorites()
        monitorNetworkStatus()
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
                val gifUrls =
                    gifs.data.map { it.images?.fixedWidthDownsampled?.url } // Extract URLs to preload
                //todo preload gifs, Coil catching {preloadGifs}
                preloadGifs(gifUrls, context = context)
                _uiState.value = HomeUiState(
                    gifsData = gifs.data.toTrendingGifList(),
                    categories = categories.data,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = HomeUiState(isLoading = false, errorMessage = e.message)
                monitorNetworkStatus()
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

    private fun preloadGifs(gifUrls: List<String?>, context: Context) {
        val imageLoader = ImageLoader(context)
        gifUrls.forEach { url ->
            val request = ImageRequest.Builder(context)
                .data(url)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()

            imageLoader.enqueue(request)
        }
    }

    private fun monitorNetworkStatus() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { isConnected ->
                if (!isConnected) {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = "No internet connection",
                            action = SnackbarAction("Retry") {
                                retryFetchingData()
                            }
                        )
                    )
                }
            }
        }
    }

    private fun retryFetchingData() = fetchTrendingAndCategoriesGiphy()

    override fun onCleared() {
        super.onCleared()
        networkMonitor.unregisterNetworkCallback()
    }
}