package com.example.gifbrowserapp.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalTrendingGif(
    @PrimaryKey
    val id: String,
    val originalGifUrl: String,
    val sampledGif :String,
    val webGifUrl: String,
)
