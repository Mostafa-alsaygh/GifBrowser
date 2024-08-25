package com.example.gifbrowserapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gifbrowserapp.data.entities.local.FavoriteGif

@Database(
    //todo make two tables for favorite and cash trending gifs
    entities = [FavoriteGif::class] ,
    version = 1
)
abstract class LocalGifDatabase : RoomDatabase() {

    abstract val favoriteGifDao: FavoriteGifDao

}