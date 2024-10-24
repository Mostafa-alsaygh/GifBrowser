package com.example.gifbrowserapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.gifbrowserapp.BuildConfig
import com.example.gifbrowserapp.data.local.FavoriteGifDao
import com.example.gifbrowserapp.data.local.LocalGifDatabase
import com.example.gifbrowserapp.data.local.TrendingGifDao
import com.example.gifbrowserapp.data.remote.service.GiphyApiService
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepository
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepositoryImpl
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.data.repository.LocalGifsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GiphyModule {
    @Provides
    @Singleton
    fun provideGiphyApi(): GiphyApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApiService::class.java)

    }

    @Provides
    @Singleton
    fun providesNetworkGiphyRepository(giphyApi: GiphyApiService): NetworkGiphyRepository {
        return NetworkGiphyRepositoryImpl(giphyApi = giphyApi)
    }

    @Provides
    @Singleton
    fun providesLocalGifDatabase(
        @ApplicationContext
        context: Context
    ): LocalGifDatabase =
        Room.databaseBuilder(context, LocalGifDatabase::class.java, "LocalGifDb")
            .build()

    @Provides
    @Singleton
    fun providesFavoriteGifDao(
        favoriteGifDatabase: LocalGifDatabase
    ): FavoriteGifDao = favoriteGifDatabase.favoriteGifDao

    @Provides
    @Singleton
    fun providesTrendingGifDao(
        trendingGifDatabase: LocalGifDatabase
    ): TrendingGifDao = trendingGifDatabase.trendingGifDao

    @Provides
    @Singleton
    fun providesLocalGifsRepository(
        favoriteGifDao: FavoriteGifDao, trendingGifDao: TrendingGifDao
    ): LocalGifsRepository =
        LocalGifsRepositoryImpl(favoriteGifDao = favoriteGifDao, trendingGifDao = trendingGifDao)

}