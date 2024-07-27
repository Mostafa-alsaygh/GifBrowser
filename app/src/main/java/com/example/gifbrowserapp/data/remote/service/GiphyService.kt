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
    ): ApiResponseRemote<GifData>

    @GET("v1/gifs/categories")
    suspend fun getCategories(
        @Query("api_key") apiKey: String,
    ):ApiResponseRemote<CategoryData>


    @GET("v1/gifs/search")
    suspend fun getSearchData(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ):ApiResponseRemote<GifData>
}
