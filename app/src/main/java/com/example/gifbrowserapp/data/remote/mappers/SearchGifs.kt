package com.example.gifbrowserapp.data.remote.mappers

import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.presentation.features.home.GifImages
import com.example.gifbrowserapp.presentation.features.home.TrendingGif
import com.example.gifbrowserapp.presentation.features.search.SearchedGif
import com.example.gifbrowserapp.presentation.features.search.SearchedGifImages

fun List<GifData>.toSearchedGifList(): List<SearchedGif> {
    return this.map { it.toSearchedGif() }
}

fun GifData.toSearchedGif(): SearchedGif {
    return SearchedGif(
        id = this.id,
        url = this.url ?: "",
        title = this.title ?: "",
        images = SearchedGifImages(
            original = this.images?.original?.url ?: "",
            fixedWidthDownsampled = this.images?.fixedWidthDownsampled?.url ?: ""
        )
    )
}