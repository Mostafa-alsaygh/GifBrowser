package com.example.gifbrowserapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
import com.example.gifbrowserapp.data.entities.local.ResentSearch
import com.example.gifbrowserapp.data.local.FavoriteGifDao
import com.example.gifbrowserapp.data.local.TrendingGifDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class LocalGifsRepositoryImpl @Inject constructor(
    private val favoriteGifDao: FavoriteGifDao,
    private val trendingGifDao: TrendingGifDao,
    private val dataStore: DataStore<List<ResentSearch>>,
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


    override suspend fun getRecentSearches(): List<ResentSearch> {
        return dataStore.data.firstOrNull() ?: emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override suspend fun saveRecentSearch(search: ResentSearch) {
        val currentSearches = getRecentSearches().toMutableList()

        currentSearches.removeAll { it.searchQuery == search.searchQuery }

        currentSearches.add(0, search)

        if (currentSearches.size > 10) {
            currentSearches.removeAt(currentSearches.size - 1)
        }

        dataStore.updateData { currentSearches }
    }


}