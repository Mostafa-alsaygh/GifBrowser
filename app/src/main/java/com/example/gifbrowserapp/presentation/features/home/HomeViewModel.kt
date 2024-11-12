package com.example.gifbrowserapp.presentation.features.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.remote.mappers.toTrendingGifList
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepository
import com.example.gifbrowserapp.data.utils.NetworkMonitor
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifState
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
        monitorNetworkStatus()
    }

    override fun fetchTrendingAndCategoriesGiphy() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            if (networkMonitor.isConnected.value) {
                fetchTrendingAndCategoriesAndSaveLocally()
            } else {

                loadTrendingGifsFromCache()
            }
        }
    }

    private suspend fun fetchTrendingAndCategoriesAndSaveLocally() {
        try {
            val trendingGifs = networkGiphyRepository.takeTrendingGifs()
            val categories = networkGiphyRepository.takeCategoriesOfGiphy()

            val gifUrls = trendingGifs.data.map { it.images?.fixedWidthDownsampled?.url }
            preloadGifs(gifUrls, context)

            localGifsRepository.addTrendingGifs(trendingGifs.data.toLocalTrendingGifsList())

            _uiState.value = HomeUiState(
                gifsData = trendingGifs.data.toTrendingGifList(),
                categories = categories.data,
                isLoading = false
            )

        } catch (e: Exception) {
            _uiState.value = HomeUiState(isLoading = false, errorMessage = e.message)
            monitorNetworkStatus()
        }
    }


    private suspend fun loadTrendingGifsFromCache() {
        try {
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


    override fun loadFavoriteGif() {
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
                _uiState.value = _uiState.value.copy(isNoInternetConnection = isConnected.not())
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
