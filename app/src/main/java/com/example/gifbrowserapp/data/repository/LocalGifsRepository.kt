package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
import kotlinx.coroutines.flow.Flow

interface LocalGifsRepository {

    // Favorite Gifs

    fun getFavoriteGifs(): Flow<List<FavoriteGif>>

    suspend fun getFavoriteById(id: String): FavoriteGif

    suspend fun addFavoriteGif(favoriteGif: FavoriteGif)

    suspend fun removeFavoriteGif(favoriteGif: FavoriteGif)

    // Trending Gifs

    suspend fun getTrendingGifs(): Flow<List<LocalTrendingGif>>

    suspend fun addTrendingGifs(trendingGifs: List<LocalTrendingGif>)



}