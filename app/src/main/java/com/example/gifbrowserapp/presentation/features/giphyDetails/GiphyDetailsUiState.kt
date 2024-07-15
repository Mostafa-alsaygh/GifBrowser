package com.example.gifbrowserapp.presentation.features.giphyDetails

import com.example.gifbrowserapp.data.entities.Original
import com.example.gifbrowserapp.data.entities.gifData.GifData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class GiphyDetailsUiState(
    //todo {val gif data}} is right i thing need mapper?
    val gifData: Original =Original(),
    val isLoading: Boolean = false
)
