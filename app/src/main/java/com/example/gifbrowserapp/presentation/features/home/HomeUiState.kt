package com.example.gifbrowserapp.presentation.features.home

import com.example.gifbrowserapp.data.entities.gifData.GifData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
data class HomeUiState(

    val gifData: List<GifData> = emptyList(),
    val searchQuery: Flow<String> = emptyFlow(),
    val isLoading: Boolean = false

)
