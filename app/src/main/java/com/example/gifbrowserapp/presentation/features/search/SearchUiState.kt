package com.example.gifbrowserapp.presentation.features.search

import com.example.gifbrowserapp.data.entities.gifData.GifData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


data class SearchUiState(
    val gifData: List<GifData> = emptyList(),
    val searchQuery: Flow<String> = emptyFlow(),
    val isLoading: Boolean = false
)
