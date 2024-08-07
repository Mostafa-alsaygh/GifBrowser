package com.example.gifbrowserapp.presentation.features.home


interface HomeEvent {

    object NavigateBack : HomeEvent
    object NavigateToSearchScreen : HomeEvent
    object NavigateToSearchScreenWithCategoryName:HomeEvent
    object NavigateToGiphyDetailsScreen : HomeEvent
}