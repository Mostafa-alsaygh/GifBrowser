package com.example.gifbrowserapp.data.repository

import android.app.Application
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.data.entities.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.data.remote.service.GiphyApiService
import com.example.gifbrowserapp.data.utils.ApiUtils

class GiphyRepositoryImpl(
    private val giphyApi: GiphyApiService,
    private val appContext: Application
) : GiphyRepository {


    init {
        val appContext = appContext.getString(R.string.app_name)
        println("hi from repository the app name is $appContext")
    }

    override suspend fun takeTrendingGifs(): ApiResponseRemote<GifData> {

        return giphyApi.getTrendingGifs(
            apiKey = ApiUtils.API_KEY,
            limit = 25,
            offset = 0,
            rating = "g",
            bundle = "messaging_non_clips"
        )
    }
}