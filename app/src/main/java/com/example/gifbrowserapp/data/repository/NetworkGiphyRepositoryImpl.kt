package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.BuildConfig
import com.example.gifbrowserapp.data.entities.remote.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.remote.categories.CategoryData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifData
import com.example.gifbrowserapp.data.remote.service.GiphyApiService
import javax.inject.Inject

class NetworkGiphyRepositoryImpl @Inject constructor(
    private val giphyApi: GiphyApiService,
) : NetworkGiphyRepository {


    override suspend fun takeTrendingGifs(): ApiResponseRemote<GifData> {

        return giphyApi.getTrendingGifs(
            apiKey = BuildConfig.API_KEY,
            limit = 50,
            offset = 0,
        )
    }

    override suspend fun takeCategoriesOfGiphy(): ApiResponseRemote<CategoryData> {
        return giphyApi.getCategories(apiKey = BuildConfig.API_KEY)
    }



    override suspend fun takeSearchData(query: String): ApiResponseRemote<GifData> {
        return giphyApi.getSearchData(apiKey = BuildConfig.API_KEY, query = query, limit = 10, offset = 0)
    }
}