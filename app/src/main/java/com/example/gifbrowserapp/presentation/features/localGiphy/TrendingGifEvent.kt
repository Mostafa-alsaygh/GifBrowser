package com.example.gifbrowserapp.presentation.features.localGiphy

import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif

interface TrendingGifEvent {
    object LoadTrending : TrendingGifEvent
    data class AddLastTrendingGifs(val trendingGifs: List<LocalTrendingGif>) : TrendingGifEvent
}