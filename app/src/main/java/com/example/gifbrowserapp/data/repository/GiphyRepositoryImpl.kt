package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.categories.CategoryData
import com.example.gifbrowserapp.data.entities.gifData.GifData
import com.example.gifbrowserapp.data.remote.service.GiphyApiService
import com.example.gifbrowserapp.data.utils.ApiUtils
import javax.inject.Inject

class GiphyRepositoryImpl @Inject constructor(
    private val giphyApi: GiphyApiService,
) : GiphyRepository {


    override suspend fun takeTrendingGifs(): ApiResponseRemote<GifData> {

        return giphyApi.getTrendingGifs(
            apiKey = ApiUtils.API_KEY,
            limit = 25,
            offset = 0,
            rating = "g",
            bundle = "messaging_non_clips"
        )
    }

    override suspend fun takeCategoriesOfGiphy(): ApiResponseRemote<CategoryData> {
        return  giphyApi.getCategoriesOfGiphy(apiKey = ApiUtils.API_KEY)
    }
}