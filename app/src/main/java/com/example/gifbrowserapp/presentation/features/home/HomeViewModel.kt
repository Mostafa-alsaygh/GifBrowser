package com.example.gifbrowserapp.presentation.features.home

import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.data.remote.service.RetrofitInstance
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()),
    HomeInteractionListener {
    private val apiKey = "IAE3fY0ekIEgxc6Wnzfi9NTrwIYBtE7t"

    private val _trendingGifs = MutableStateFlow<List<GifData>>(emptyList())
    val trendingGifs: StateFlow<List<GifData>> = _trendingGifs


    init {
        fetchTrendingGifs()
    }

    private fun fetchTrendingGifs() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTrendingGifs(
                    apiKey = apiKey,
                    limit = 25,
                    offset = 0,
                    rating = "g",
                    bundle = "messaging_non_clips"
                )
                _trendingGifs.value = response.data
                Timber.d("Fetched ${response.data} gifs")

            } catch (e: Exception) {
                Timber.e(e, "Error fetching trending gifs")
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