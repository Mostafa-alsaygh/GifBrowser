package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import kotlinx.coroutines.flow.Flow

interface LocalGifsRepository {
    suspend fun getFavoriteById(id: Int): FavoriteGif

    suspend fun getFavoriteGifs(): Flow<List<FavoriteGif>>

    suspend fun addFavoriteGif(favoriteGif: FavoriteGif)

    suspend fun removeFavoriteGif(favoriteGif: FavoriteGif)



}