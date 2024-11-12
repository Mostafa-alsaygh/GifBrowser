package com.example.gifbrowserapp.present

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gifbrowserapp.data.FakeData
import com.example.gifbrowserapp.data.entities.remote.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.remote.FixedWidthDownsampled
import com.example.gifbrowserapp.data.entities.remote.Meta
import com.example.gifbrowserapp.data.entities.remote.Original
import com.example.gifbrowserapp.data.entities.remote.gifData.GifData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifImages
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepository
import com.example.gifbrowserapp.data.utils.NetworkMonitor
import com.example.gifbrowserapp.presentation.features.home.HomeViewModel
import com.example.gifbrowserapp.presentation.features.home.TrendingGif
import com.example.gifbrowserapp.presentation.features.localGiphy.FavoriteGifEvent
import com.example.gifbrowserapp.presentation.features.localGiphy.TrendingGifEvent
import com.example.gifbrowserapp.presentation.utils.extensions.toFavoriteGif
import com.example.gifbrowserapp.presentation.utils.extensions.toGifItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel
    private lateinit var networkGiphyRepository: NetworkGiphyRepository
    private lateinit var localGifsRepository: LocalGifsRepository
    private lateinit var networkMonitor: NetworkMonitor

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        networkGiphyRepository = mockk()
        localGifsRepository = mockk()
        networkMonitor = mockk(relaxed = true)  // Relaxed mode for default empty behavior

        viewModel = HomeViewModel(
            context = mockk(relaxed = true),
            networkGiphyRepository = networkGiphyRepository,
            localGifsRepository = localGifsRepository,
            networkMonitor = networkMonitor
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTrendingAndCategoriesGiphy should load trending GIFs from network when connected`() =
        runTest {
            val mockGifs = FakeData.listOfTrendingGifs

            val isConnectedFlow = MutableStateFlow(true)
            coEvery { networkMonitor.isConnected } returns isConnectedFlow
            coEvery { networkGiphyRepository.takeTrendingGifs() } returns mockGifs.toApiResponse()
            coEvery { localGifsRepository.addTrendingGifs(any()) } returns Unit
            coEvery { localGifsRepository.getFavoriteGifs() } returns flowOf(emptyList())

            viewModel.onEvent(
                trendingGifEvent = TrendingGifEvent.LoadTrending,
                favoriteGifEvent = FavoriteGifEvent.LoadFavorites
            )
            advanceUntilIdle()

            coVerify { networkGiphyRepository.takeTrendingGifs() }
            coVerify { localGifsRepository.addTrendingGifs(any()) }  // Ensure addTrendingGifs is called

            with(viewModel.uiState.value) {
                assertTrue(
                    "Expected gifsData to be populated but found it empty.",
                    gifsData.isNotEmpty()
                )
                assertEquals(mockGifs, gifsData)
                assertFalse(isLoading)
            }
        }


    @Test
    fun `fetchTrendingAndCategoriesGiphy should load from cache when network is disconnected`() =
        runTest {
            val cachedGifs = FakeData.LocalTrendingGifs
            coEvery { networkMonitor.isConnected } returns MutableStateFlow(false)
            coEvery { localGifsRepository.getTrendingGifs() } returns flowOf(cachedGifs)

            viewModel.onEvent(
                trendingGifEvent = TrendingGifEvent.LoadTrending,
                favoriteGifEvent = FavoriteGifEvent.LoadFavorites
            )
            advanceUntilIdle()

            Assert.assertEquals(cachedGifs, viewModel.uiState.value.gifsData)
            Assert.assertEquals(false, viewModel.uiState.value.isLoading)
        }

    @Test
    fun `loadFavorites should update favoriteGifState with cached favorites`() = runTest {
        val favoriteGifs = FakeData.listOfTrendingGifs.map { it.toGifItem().toFavoriteGif() }
        coEvery { localGifsRepository.getFavoriteGifs() } returns flowOf(favoriteGifs)

        viewModel.onEvent(TrendingGifEvent.LoadTrending, FavoriteGifEvent.LoadFavorites)
        advanceUntilIdle()

        Assert.assertEquals(favoriteGifs, viewModel.favoriteGifState.value.favoriteGifs)
        Assert.assertEquals(false, viewModel.favoriteGifState.value.isLoading)
    }

    @Test
    fun `monitorNetworkStatus should send snackbar event when disconnected`() = runTest {
        coEvery { networkMonitor.isConnected } returns MutableStateFlow(false)

//        viewModel.monitorNetworkStatus()
        advanceUntilIdle()

        // Here you should verify the SnackbarController received an event
        // For instance:
        // coVerify { SnackbarController.sendEvent(any()) }
    }
}


fun List<TrendingGif>.toApiResponse(): ApiResponseRemote<GifData> {
    return ApiResponseRemote(
        data = this.map { gif ->
            GifData(
                id = gif.id,
                url = gif.url,
                title = gif.title,
                images = GifImages(
                    fixedWidthDownsampled = FixedWidthDownsampled(
                        size = "",
                        url = gif.images.fixedWidthDownsampled
                    ),
                    original = Original(size = "", url = gif.images.original)
                )
            )
        },
        meta = Meta(status = 200, msg = "")
    )
}