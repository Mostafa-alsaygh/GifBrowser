package com.example.gifbrowserapp.data

import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
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

    fun getLocalMockTrendingGifs(): List<LocalTrendingGif> {
        return listOf(
            LocalTrendingGif(
                id = "1",
                originalGifUrl = "https://example.com/original1.gif",
                sampledGif = "https://example.com/sample1.gif",
                webGifUrl = "https://example.com/web1"
            ),
            LocalTrendingGif(
                id = "2",
                originalGifUrl = "https://example.com/original2.gif",
                sampledGif = "https://example.com/sample2.gif",
                webGifUrl = "https://example.com/web2"
            ),
            LocalTrendingGif(
                id = "3",
                originalGifUrl = "https://example.com/original3.gif",
                sampledGif = "https://example.com/sample3.gif",
                webGifUrl = "https://example.com/web3"
            ),
            LocalTrendingGif(
                id = "4",
                originalGifUrl = "https://example.com/original4.gif",
                sampledGif = "https://example.com/sample4.gif",
                webGifUrl = "https://example.com/web4"
            ),
            LocalTrendingGif(
                id = "5",
                originalGifUrl = "https://example.com/original5.gif",
                sampledGif = "https://example.com/sample5.gif",
                webGifUrl = "https://example.com/web5"
            )
        )
    }
}