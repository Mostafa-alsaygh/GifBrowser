package com.example.gifbrowserapp.presentation.features.home

import com.example.gifbrowserapp.data.entities.gifData.GifData

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

fun List<GifData>.toTrendingGifList(): List<TrendingGif> {
    return this.map { it.toTrendingGif() }
}