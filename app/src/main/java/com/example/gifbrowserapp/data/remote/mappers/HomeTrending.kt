package com.example.gifbrowserapp.data.remote.mappers

import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.presentation.features.home.GifImages
import com.example.gifbrowserapp.presentation.features.home.TrendingGif

fun List<GifData>.toTrendingGifList(): List<TrendingGif> {
    return this.map { it.toTrendingGif() }
}

fun GifData.toTrendingGif(): TrendingGif {
    return TrendingGif(
        id = this.id,
        url = this.url ?: "",
        title = this.title ?: "",
        images = GifImages(
            original = this.images?.original?.url ?: "",
            fixedWidthDownsampled = this.images?.fixedWidthDownsampled?.url ?: ""
        )
    )
}