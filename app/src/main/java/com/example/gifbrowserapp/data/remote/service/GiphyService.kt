package com.example.gifbrowserapp.data.remote.service

import com.example.gifbrowserapp.data.entities.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.categories.CategoryData
import com.example.gifbrowserapp.data.entities.gifData.GifData
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyApiService {
    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("bundle") bundle: String
    ): ApiResponseRemote<GifData>

    @GET("v1/gifs/categories")
    suspend fun getCategoriesOfGiphy(
        @Query("api_key") apiKey: String,
    ):ApiResponseRemote<CategoryData>
}
