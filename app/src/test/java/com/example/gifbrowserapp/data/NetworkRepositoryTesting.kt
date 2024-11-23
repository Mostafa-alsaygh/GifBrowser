package com.example.gifbrowserapp.data

import com.example.gifbrowserapp.data.entities.remote.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.remote.categories.CategoryData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifData
import com.example.gifbrowserapp.data.remote.service.GiphyApiService
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepository
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NetworkRepositoryTesting {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var mockGiphyApiService: GiphyApiService
    private lateinit var networkGiphyRepository: NetworkGiphyRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockGiphyApiService = spyk<FakeGiphyApiService>()
        networkGiphyRepository = NetworkGiphyRepositoryImpl(mockGiphyApiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `takeTrendingGifs should return trending gifs from API`() = runTest {

        val mockTrendingResponse = RepositoryFakeData.getMockTrendingGifs()

        coEvery { mockGiphyApiService.getTrendingGifs(any(), any(), any()) } returns mockTrendingResponse

        val result = networkGiphyRepository.takeTrendingGifs()
        assertEquals(mockTrendingResponse, result)

        coVerify { mockGiphyApiService.getTrendingGifs(any(), any(), any()) }
    }

    @Test
    fun `takeCategoriesOfGiphy should return categories from API`() = runTest {
        val mockCategoriesResponse = RepositoryFakeData.getMockCategories()
        coEvery { mockGiphyApiService.getCategories(any()) } returns mockCategoriesResponse

        val result = networkGiphyRepository.takeCategoriesOfGiphy()
        assertEquals(mockCategoriesResponse, result)

        coVerify { mockGiphyApiService.getCategories(any()) }
    }

    @Test
    fun `takeSearchData should return search data from API`() = runTest {
        val mockSearchResponse = RepositoryFakeData.getMockSearchResults()
        val query = "funny"
        coEvery {
            mockGiphyApiService.getSearchData(
                any(),
                query,
                any(),
                any()
            )
        } returns mockSearchResponse

        val result = networkGiphyRepository.takeSearchData(query)
        assertEquals(mockSearchResponse, result)
        coVerify { mockGiphyApiService.getSearchData(any(), query, any(), any()) }
    }
}



class FakeGiphyApiService : GiphyApiService {

    override suspend fun getTrendingGifs(
        apiKey: String,
        limit: Int,
        offset: Int
    ): ApiResponseRemote<GifData> {
        return RepositoryFakeData.getMockTrendingGifs()
    }

    override suspend fun getCategories(apiKey: String): ApiResponseRemote<CategoryData> {
        return RepositoryFakeData.getMockCategories()
    }

    override suspend fun getSearchData(
        apiKey: String,
        query: String,
        limit: Int,
        offset: Int
    ): ApiResponseRemote<GifData> {
        return RepositoryFakeData.getMockSearchResults()
    }
}