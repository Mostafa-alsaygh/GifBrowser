package com.example.gifbrowserapp.data.entities.local

import kotlinx.serialization.Serializable

@Serializable
data class GifItem(
    val id: String,
    val url: String,
    val images: GifImage
)

@Serializable
data class GifImage(
    val original: String,
    val fixedWidthDownsampled: String
)