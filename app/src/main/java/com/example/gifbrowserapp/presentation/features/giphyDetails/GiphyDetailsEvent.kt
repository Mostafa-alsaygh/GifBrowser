package com.example.gifbrowserapp.presentation.features.giphyDetails

import com.example.gifbrowserapp.presentation.features.search.SearchEvent

interface GiphyDetailsEvent {
    object NavigateBack : SearchEvent
    object NavigateToHomeScreen : SearchEvent
}