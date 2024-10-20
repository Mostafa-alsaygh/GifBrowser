package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.remote.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.remote.categories.CategoryData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifData

interface NetworkGiphyRepository {
    suspend fun takeTrendingGifs(): ApiResponseRemote<GifData>

    suspend fun takeCategoriesOfGiphy(): ApiResponseRemote<CategoryData>

    suspend fun takeSearchData(query: String): ApiResponseRemote<GifData>
}