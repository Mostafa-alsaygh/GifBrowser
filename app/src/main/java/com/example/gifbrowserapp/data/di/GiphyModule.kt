package com.example.gifbrowserapp.data.di

import com.example.gifbrowserapp.data.remote.service.GiphyApiService
import com.example.gifbrowserapp.data.repository.GiphyRepository
import com.example.gifbrowserapp.data.repository.GiphyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
            .baseUrl("https://api.giphy.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApiService::class.java)

    }

    @Provides
    @Singleton
    fun providesGiphyRepository(giphyApi: GiphyApiService): GiphyRepository {
        return GiphyRepositoryImpl(giphyApi = giphyApi)
    }

}