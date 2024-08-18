package com.example.gifbrowserapp.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteGif(
    @PrimaryKey
    val id: Int,
    val originalGifUrl: String,
    val webGifUrl: String,
    val date: Long
)
