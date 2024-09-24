package com.example.gifbrowserapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif

@Database(
    entities = [FavoriteGif::class, LocalTrendingGif::class ] ,
    version = 2
)
abstract class LocalGifDatabase : RoomDatabase() {

    abstract val favoriteGifDao: FavoriteGifDao

    abstract val trendingGifDao: TrendingGifDao

}