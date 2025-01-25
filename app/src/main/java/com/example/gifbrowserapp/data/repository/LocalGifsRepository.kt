package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
import com.example.gifbrowserapp.data.entities.local.ResentSearch
import kotlinx.coroutines.flow.Flow

interface LocalGifsRepository {

    fun getFavoriteGifs(): Flow<List<FavoriteGif>>

    suspend fun getFavoriteById(id: String): FavoriteGif

    suspend fun addFavoriteGif(favoriteGif: FavoriteGif)

    suspend fun removeFavoriteGif(favoriteGif: FavoriteGif)


    suspend fun getTrendingGifs(): Flow<List<LocalTrendingGif>>

    suspend fun addTrendingGifs(trendingGifs: List<LocalTrendingGif>)


    suspend fun getRecentSearches(): List<ResentSearch>

    suspend fun saveRecentSearch(search: ResentSearch)

}