package com.example.gifbrowserapp.presentation.features.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.entities.local.ResentSearch
import com.example.gifbrowserapp.data.remote.mappers.toSearchedGifList
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString
import com.example.gifbrowserapp.presentation.utils.extensions.toGifItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val giphyRepository: NetworkGiphyRepository,
    private val giphyLocalRepository: LocalGifsRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchUiState, SearchEvent>(SearchUiState()), SearchInteractionListener {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> get() = _uiState


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _recentSearches = MutableStateFlow<List<ResentSearch>>(emptyList())
    val recentSearches: StateFlow<List<ResentSearch>> get() = _recentSearches

    init {
        loadRecentSearches()
        searchGifs()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun searchGifs() {
        viewModelScope.launch {
            _searchQuery
                .debounce(800)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        emit(SearchUiState(isLoading = true))
                        try {
                            val searchResults = giphyRepository.takeSearchData(query)
                            saveSearchQuery(query)
                            emit(
                                SearchUiState(
                                    gifsData = searchResults.data.toSearchedGifList(),
                                    isLoading = false
                                )
                            )
                        } catch (e: Exception) {
                            emit(SearchUiState(isLoading = false, errorMessage = e.message))
                        }
                    }
                }
                .collect { newState ->
                    _uiState.value = newState
                }
        }
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            _recentSearches.value = giphyLocalRepository.getRecentSearches()
        }
    }

    private fun saveSearchQuery(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                giphyLocalRepository.saveRecentSearch(ResentSearch(searchQuery = query))
                loadRecentSearches()
            }
        }
    }

    fun setCategoryName(categoryName: String) {
        _searchQuery.value = categoryName.takeIf { it != "{categoryName}" } ?: emptyString()
    }

    override fun onClearSearch() {
        _searchQuery.value = emptyString()
    }

    override fun onSearchQueryChange(value: String) {
        _searchQuery.value = value
    }

    override fun navigateBack() {
        viewModelScope.launch {
            emitNewEvent(SearchEvent.NavigateBack)
        }
    }

    override fun onClickGif(searchedGif: SearchedGif) {
        val gifItem = searchedGif.toGifItem()
        _uiState.value = _uiState.value.copy(selectedGif = gifItem)
        emitNewEvent(SearchEvent.NavigateToGiphyDetailsScreen)
    }
}