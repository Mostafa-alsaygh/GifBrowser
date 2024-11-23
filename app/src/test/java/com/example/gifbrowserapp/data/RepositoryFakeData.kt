package com.example.gifbrowserapp.data

import com.example.gifbrowserapp.data.entities.remote.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.remote.FixedWidthDownsampled
import com.example.gifbrowserapp.data.entities.remote.Meta
import com.example.gifbrowserapp.data.entities.remote.Original
import com.example.gifbrowserapp.data.entities.remote.categories.CategoryData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifImages

object RepositoryFakeData {

    private val listOfGifData = listOf(
        GifData(
            id = "1",
            title = "Funny GIF",
            url = "https://example.com/fixed_width_downsampled.gif",
            images = GifImages(
                fixedWidthDownsampled = FixedWidthDownsampled(
                    size = "15000",
                    url = "https://example.com/fixed_width_downsampled.gif"
                ),
                original = Original(
                    size = "25000",
                    url = "https://example.com/original.gif"
                )
            )
        ),
        GifData(
            id = "2",
            title = "Funny GIF",
            url = "https://example.com/fixed_width_downsampled.gif",
            images = GifImages(
                fixedWidthDownsampled = FixedWidthDownsampled(
                    size = "15000",
                    url = "https://example.com/fixed_width_downsampled.gif"
                ),
                original = Original(
                    size = "25000",
                    url = "https://example.com/original.gif"
                )
            )
        ),
    )

    fun getMockTrendingGifs(): ApiResponseRemote<GifData> {
        return ApiResponseRemote(
            data = listOfGifData,
            meta = Meta(status = 200, msg = "")
        )
    }

    fun getMockCategories(): ApiResponseRemote<CategoryData> {
        return ApiResponseRemote(
            data = listOf(
                CategoryData(
                    name = "funny", gif = GifData(
                        id = "2",
                        title = "Funny GIF",
                        url = "https://example.com/fixed_width_downsampled.gif",
                        images = GifImages(
                            fixedWidthDownsampled = FixedWidthDownsampled(
                                size = "15000",
                                url = "https://example.com/fixed_width_downsampled.gif"
                            ),
                            original = Original(
                                size = "25000",
                                url = "https://example.com/original.gif"
                            )
                        )
                    )
                ),
                CategoryData(
                    name = "Cool", gif = GifData(
                        id = "2",
                        title = "Funny GIF",
                        url = "https://example.com/fixed_width_downsampled.gif",
                        images = GifImages(
                            fixedWidthDownsampled = FixedWidthDownsampled(
                                size = "15000",
                                url = "https://example.com/fixed_width_downsampled.gif"
                            ),
                            original = Original(
                                size = "25000",
                                url = "https://example.com/original.gif"
                            )
                        )
                    )
                )
            ),
            meta = Meta(status = 200, msg = "")
        )
    }

    fun getMockSearchResults(): ApiResponseRemote<GifData> {
        return ApiResponseRemote(
            data = listOfGifData,
            meta = Meta(status = 200, msg = "")
        )
    }
}