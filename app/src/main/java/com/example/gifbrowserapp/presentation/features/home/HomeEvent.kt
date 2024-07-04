package com.example.gifbrowserapp.presentation.features.home

import com.example.gifbrowserapp.presentation.features.search.SearchEvent

interface HomeEvent {

    object NavigateBack : SearchEvent
    object NavigateSearchScreen : SearchEvent

    object NavigateToDetailGif : SearchEvent
}