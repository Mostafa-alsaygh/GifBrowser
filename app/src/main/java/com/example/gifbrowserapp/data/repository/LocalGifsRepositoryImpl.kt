package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.local.FavoriteGifDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalGifsRepositoryImpl @Inject constructor(
    private val favoriteGifDao: FavoriteGifDao
) : LocalGifsRepository {
    override suspend fun takeFavoriteGifs(): Flow<List<FavoriteGif>> {
        TODO("Not yet implemented")
    }


}
