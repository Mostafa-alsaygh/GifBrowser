package com.example.gifbrowserapp.data.remote.service

import com.example.gifbrowserapp.data.entities.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.gifData.GifData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


}

object RetrofitInstance {
    private const val BASE_URL = "https://api.giphy.com/"

    val api: GiphyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApiService::class.java)
    }
}


























//
//interface GiphyService {
//
//    @GET("trending")
//    suspend fun getTrending(): Response<ApiResponseRemote<GifData>>
//
//}
//
//
//object RetrofitGiphyHelper {
//
//    fun getGiphy(): Retrofit {
//        return Retrofit.Builder().baseUrl("https://api.giphy.com/v1/gifs/")
//            .addConverterFactory(GsonConverterFactory.create()).build()
//    }
//}



//var retrofit: Retrofit = Retrofit.Builder()
//    .baseUrl("https://api.giphy.com/v1/gifs/")
//    .build()
//
//var service: GiphyService = retrofit.create(GiphyService::class.java)
//
//
//var repos: Response<ApiResponseRemote<GifData>> = service