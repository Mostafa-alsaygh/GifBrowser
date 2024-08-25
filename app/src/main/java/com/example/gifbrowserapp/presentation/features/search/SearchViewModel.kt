package com.example.gifbrowserapp.presentation.features.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.remote.mappers.toSearchedGifList
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.presentation.features.base.BaseViewModel
import com.example.gifbrowserapp.presentation.utils.extensions.emptyString
import com.example.gifbrowserapp.presentation.utils.extensions.toGifItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchUiState, SearchEvent>(SearchUiState()), SearchInteractionListener {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> get() = _uiState


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    init {
        searchGifs()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun searchGifs() {
        viewModelScope.launch {
            _searchQuery
                .debounce(600)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        emit(SearchUiState(isLoading = true))
                        try {
                            val searchResults = giphyRepository.takeSearchData(query)
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

    fun setCategoryName(categoryName: String) {
        _searchQuery.value = categoryName
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
