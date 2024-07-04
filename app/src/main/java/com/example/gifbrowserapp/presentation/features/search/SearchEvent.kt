package com.example.gifbrowserapp.presentation.features.search


interface SearchEvent {
    object NavigateBack : SearchEvent
    object NavigateToDetailGif : SearchEvent
}
