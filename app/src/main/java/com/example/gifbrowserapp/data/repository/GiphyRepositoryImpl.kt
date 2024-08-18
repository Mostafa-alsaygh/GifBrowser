package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.remote.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.remote.categories.CategoryData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifData
import com.example.gifbrowserapp.data.remote.service.GiphyApiService
import com.example.gifbrowserapp.data.utils.ApiUtils
import javax.inject.Inject

class GiphyRepositoryImpl @Inject constructor(
    private val giphyApi: GiphyApiService,
) : GiphyRepository {


    override suspend fun takeTrendingGifs(): ApiResponseRemote<GifData> {

        return giphyApi.getTrendingGifs(
            apiKey = ApiUtils.API_KEY,
            limit = 50,
            offset = 0,
        )
    }

    override suspend fun takeCategoriesOfGiphy(): ApiResponseRemote<CategoryData> {
        return giphyApi.getCategories(apiKey = ApiUtils.API_KEY)
    }



    override suspend fun takeSearchData(query: String): ApiResponseRemote<GifData> {
        return giphyApi.getSearchData(apiKey = ApiUtils.API_KEY, query = query, limit = 10, offset = 0)
    }
}