package com.example.gifbrowserapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGifDao {
    @Query("SELECT * FROM FavoriteGif WHERE id = :id")
    fun getFavoriteGifById(id: String): FavoriteGif

    @Query("SELECT * FROM FavoriteGif ORDER BY date DESC")
    fun getFavoriteGifOrderedByDate(): Flow<List<FavoriteGif>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteGif(favoriteGif: FavoriteGif)

    @Delete
    suspend fun removeFavoriteGif(favoriteGif: FavoriteGif)


}