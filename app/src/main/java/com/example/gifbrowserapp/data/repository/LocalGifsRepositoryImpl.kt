package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
import com.example.gifbrowserapp.data.local.FavoriteGifDao
import com.example.gifbrowserapp.data.local.TrendingGifDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalGifsRepositoryImpl @Inject constructor(
    private val favoriteGifDao: FavoriteGifDao,
    private val trendingGifDao: TrendingGifDao
) : LocalGifsRepository {

    override suspend fun getFavoriteById(id: String): FavoriteGif =
        favoriteGifDao.getFavoriteGifById(id)

    override fun getFavoriteGifs(): Flow<List<FavoriteGif>> {
        return favoriteGifDao.getFavoriteGifOrderedByDate()
    }


    override suspend fun addFavoriteGif(favoriteGif: FavoriteGif) {
        favoriteGifDao.addFavoriteGif(favoriteGif)
    }

    override suspend fun removeFavoriteGif(favoriteGif: FavoriteGif) {
        favoriteGifDao.removeFavoriteGif(favoriteGif)
    }



    override suspend fun getTrendingGifs(): Flow<List<LocalTrendingGif>> {
        return trendingGifDao.getLastTrendingGifs()
    }

    override suspend fun addTrendingGifs(trendingGifs: List<LocalTrendingGif>) {
        return trendingGifDao.addLastTrendingGifs(trendingGifs)
    }


}
