package com.example.gifbrowserapp.data.repository

import com.example.gifbrowserapp.data.entities.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.categories.CategoryData
import com.example.gifbrowserapp.data.entities.gifData.GifData

interface GiphyRepository {
    suspend fun takeTrendingGifs(): ApiResponseRemote<GifData>

    suspend fun takeCategoriesOfGiphy(): ApiResponseRemote<CategoryData>

    suspend fun takeSearchData(query: String):ApiResponseRemote<GifData>
}