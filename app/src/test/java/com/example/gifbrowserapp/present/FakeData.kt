package com.example.gifbrowserapp.present

import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
import com.example.gifbrowserapp.presentation.features.home.GifImages
import com.example.gifbrowserapp.presentation.features.home.TrendingGif

object FakeData {
    val listOfTrendingGifs = listOf(
        TrendingGif(
            id = "1",
            title = "Funny Cat",
            images = GifImages(
                fixedWidthDownsampled = "https://sampledomain.com/view-original1.gif",
            )
        ),
        TrendingGif(
            id = "2",
            title = "Dancing Dog",
            images = GifImages(
                fixedWidthDownsampled = "https://sampledomain.com/view-original1.gif",
            )
        ),
        TrendingGif(
            id = "3",
            title = "Surprised Pikachu",
            images = GifImages(
                fixedWidthDownsampled = "https://sampledomain.com/view-original1.gif",
            )
        )
    )


    val LocalTrendingGifs = listOf(
        LocalTrendingGif(
            id = "1",
            originalGifUrl = "https://sampledomain.com/original1.gif",
            sampledGif = "https://sampledomain.com/sample-downsampled1.gif",
            webGifUrl = "https://sampledomain.com/view-original1.gif"
        ),
        LocalTrendingGif(
            id = "2",
            originalGifUrl = "https://sampledomain.com/original2.gif",
            sampledGif = "https://sampledomain.com/sample-downsampled2.gif",
            webGifUrl = "https://sampledomain.com/view-original2.gif"
        ),
        LocalTrendingGif(
            id = "3",
            originalGifUrl = "https://sampledomain.com/original3.gif",
            sampledGif = "https://sampledomain.com/sample-downsampled3.gif",
            webGifUrl = "https://sampledomain.com/view-original3.gif"
        )
    )
}