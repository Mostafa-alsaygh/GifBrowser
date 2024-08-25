package com.example.gifbrowserapp.data.repository

import android.util.Log
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.local.FavoriteGifDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalGifsRepositoryImpl @Inject constructor(
    private val favoriteGifDao: FavoriteGifDao
) : LocalGifsRepository {

    override suspend fun getFavoriteById(id: Int): FavoriteGif =
        favoriteGifDao.getFavoriteGifById(id)

    override suspend fun getFavoriteGifs(): Flow<List<FavoriteGif>> {
        Log.d("INREPOSITORY", "getFavoriteGifs${favoriteGifDao.getFavoriteGifOrderedByDate()}")
        return favoriteGifDao.getFavoriteGifOrderedByDate()
    }


    override suspend fun addFavoriteGif(favoriteGif: FavoriteGif) {
        Log.d("INREPOSITORY", "addFavoriteGif:${favoriteGif}")
        favoriteGifDao.addFavoriteGif(favoriteGif)
    }

    override suspend fun removeFavoriteGif(favoriteGif: FavoriteGif) {
        favoriteGifDao.removeFavoriteGif(favoriteGif)
    }


}
