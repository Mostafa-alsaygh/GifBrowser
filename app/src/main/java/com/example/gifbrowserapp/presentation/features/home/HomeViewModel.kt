package com.example.gifbrowserapp.presentation.features.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.remote.mappers.toTrendingGifList
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepository
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.data.utils.NetworkMonitor
import com.example.gifbrowserapp.presentation.components.snack_bar.SnackbarAction
import com.example.gifbrowserapp.presentation.components.snack_bar.SnackbarController
import com.example.gifbrowserapp.presentation.components.snack_bar.SnackbarEvent
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifEvent
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifState
import com.example.gifbrowserapp.presentation.features.localGiphy.TrendingGifEvent
import com.example.gifbrowserapp.presentation.utils.extensions.toGifItem
import com.example.gifbrowserapp.presentation.utils.extensions.toLocalTrendingGifsList
import com.example.gifbrowserapp.presentation.utils.extensions.toTrendingGifsFromLocal
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
    private val networkGiphyRepository: NetworkGiphyRepository,
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

    fun onEvent(favoriteGifEvent: FavoriteGifEvent, trendingGifEvent: TrendingGifEvent) {
        when (favoriteGifEvent) {
            FavoriteGifEvent.LoadFavorites -> loadFavorites()
        }

        when (trendingGifEvent) {
            TrendingGifEvent.LoadTrending -> fetchTrendingAndCategoriesGiphy()
            is TrendingGifEvent.AddLastTrendingGifs -> addLastLocalTrendingGifs(trendingGifEvent.trendingGifs)
        }
    }


    private fun fetchTrendingAndCategoriesGiphy() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            Log.d("LOADING", "Network is connected1: ${networkMonitor.isConnected.value}")
            if (networkMonitor.isConnected.value) {
                Log.d("LOADING", "Network is connected2: ${networkMonitor.isConnected.value}")
                fetchTrendingAndCategoriesAndSaveLocally()
            } else {

                Log.d("LOADING", "Network is connected{BeforecallingLoadTrendingGifsFromCache}: ${networkMonitor.isConnected.value}")
                loadTrendingGifsFromCache()
            }
        }
    }

    private suspend fun fetchTrendingAndCategoriesAndSaveLocally() {
        try {
            Log.d("LOADING", "!! the fetchTrending Called and Network is connected: ${networkMonitor.isConnected.value}")
            val gifs = networkGiphyRepository.takeTrendingGifs()
            val categories = networkGiphyRepository.takeCategoriesOfGiphy()

            val gifUrls = gifs.data.map { it.images?.fixedWidthDownsampled?.url }
            preloadGifs(gifUrls, context)

            localGifsRepository.addTrendingGifs(gifs.data.toLocalTrendingGifsList())

            _uiState.value = HomeUiState(
                gifsData = gifs.data.toTrendingGifList(),
                categories = categories.data,
                isLoading = false
            )
            Log.d("LOADING", "loaded..TrendingAndCategories$categories")

        } catch (e: Exception) {
            _uiState.value = HomeUiState(isLoading = false, errorMessage = e.message)
            monitorNetworkStatus()
        }
    }


    private suspend fun loadTrendingGifsFromCache() {
        try {
            Log.d("LOADING", "Network is connected {loadTrendingGifsFromCache}: ${networkMonitor.isConnected.value}")
            localGifsRepository.getTrendingGifs().collect { cachedTrendingGifs ->
                _uiState.value = HomeUiState(
                    gifsData = cachedTrendingGifs.toTrendingGifsFromLocal(),
                    isLoading = false,
                    errorMessage = if (cachedTrendingGifs.isEmpty()) "No cached trending GIFs" else null
                )
                monitorNetworkStatus()
            }
        } catch (e: Exception) {
            _uiState.value = HomeUiState(isLoading = false, errorMessage = e.message)
        }
    }

    private fun addLastLocalTrendingGifs(trendingGifs: List<com.example.gifbrowserapp.data.entities.local.LocalTrendingGif>) {
        viewModelScope.launch {
            try {
                localGifsRepository.addTrendingGifs(trendingGifs)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
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
                                fetchTrendingAndCategoriesGiphy()
                            }
                        )
                    )
                }
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

    override fun onCleared() {
        super.onCleared()
        networkMonitor.unregisterNetworkCallback()
    }
}