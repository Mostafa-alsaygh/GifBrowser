package com.example.gifbrowserapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingGifDao {

    @Query("SELECT * FROM LocalTrendingGif")
    fun getLastTrendingGifs(): Flow<List<LocalTrendingGif>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLastTrendingGifs(favoriteGif: List<LocalTrendingGif>)

}