package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import kotlinx.coroutines.flow.Flow

interface LocalGifsRepository {

    suspend fun takeFavoriteGifs(): Flow<List<FavoriteGif>>

}