package com.example.gifbrowserapp.present

import com.example.gifbrowserapp.data.FakeData
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.entities.local.LocalTrendingGif
import com.example.gifbrowserapp.data.entities.remote.ApiResponseRemote
import com.example.gifbrowserapp.data.entities.remote.FixedWidthDownsampled
import com.example.gifbrowserapp.data.entities.remote.Meta
import com.example.gifbrowserapp.data.entities.remote.Original
import com.example.gifbrowserapp.data.entities.remote.categories.CategoryData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifData
import com.example.gifbrowserapp.data.entities.remote.gifData.GifImages
import com.example.gifbrowserapp.data.repository.LocalGifsRepository
import com.example.gifbrowserapp.data.repository.NetworkGiphyRepository
import com.example.gifbrowserapp.data.utils.NetworkMonitor
import com.example.gifbrowserapp.presentation.features.home.HomeViewModel
import com.example.gifbrowserapp.presentation.features.home.TrendingGif
import com.example.gifbrowserapp.presentation.utils.extensions.toFavoriteGif
import com.example.gifbrowserapp.presentation.utils.extensions.toGifItem
import com.example.gifbrowserapp.presentation.utils.extensions.toTrendingGifsFromLocal
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FakeLocalGifsRepositoryImpl : LocalGifsRepository {
    override fun getFavoriteGifs(): Flow<List<FavoriteGif>> = flow { }

    override suspend fun getFavoriteById(id: String): FavoriteGif = FavoriteGif(
        id = "saperet",
        originalGifUrl = "https://duckduckgo.com/?q=purus",
        webGifUrl = "http://www.bing.com/search?q=maecenas",
        date = 7945
    )

    override suspend fun addFavoriteGif(favoriteGif: FavoriteGif) = Unit

    override suspend fun removeFavoriteGif(favoriteGif: FavoriteGif) = Unit

    override suspend fun getTrendingGifs(): Flow<List<LocalTrendingGif>> = flow { }

    override suspend fun addTrendingGifs(trendingGifs: List<LocalTrendingGif>) = Unit
}

class FakeNetworkGiphyRepositoryImpl : NetworkGiphyRepository {
    override suspend fun takeTrendingGifs(): ApiResponseRemote<GifData> = ApiResponseRemote(
        data = emptyList(),
        meta = Meta(status = 200, msg = "")
    )

    override suspend fun takeCategoriesOfGiphy(): ApiResponseRemote<CategoryData> =
        ApiResponseRemote(
            data = emptyList(),
            meta = Meta(status = 200, msg = "")
        )

    override suspend fun takeSearchData(query: String): ApiResponseRemote<GifData> =
        ApiResponseRemote(
            data = emptyList(),
            meta = Meta(status = 200, msg = "")
        )

}

class FakeNetworkMonitorImpl : NetworkMonitor {
    override val isConnected: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override fun unregisterNetworkCallback() = Unit
}

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel
    private lateinit var networkGiphyRepository: NetworkGiphyRepository
    private lateinit var localGifsRepository: LocalGifsRepository
    private lateinit var networkMonitor: NetworkMonitor

    private fun createViewModel() = HomeViewModel(
        context = spyk(),
        networkGiphyRepository = networkGiphyRepository,
        localGifsRepository = localGifsRepository,
        networkMonitor = networkMonitor
    )


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        networkGiphyRepository = spyk<FakeNetworkGiphyRepositoryImpl>()
        localGifsRepository = spyk<FakeLocalGifsRepositoryImpl>()
        networkMonitor = spyk<FakeNetworkMonitorImpl>()
        viewModel = createViewModel()
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
            every { localGifsRepository.getFavoriteGifs() } returns flowOf(emptyList())

            viewModel.fetchTrendingAndCategoriesGiphy()

            advanceUntilIdle()

            coVerify { networkGiphyRepository.takeTrendingGifs() }
            coVerify { networkGiphyRepository.takeCategoriesOfGiphy() }
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

            viewModel.fetchTrendingAndCategoriesGiphy()

            advanceUntilIdle()

            Assert.assertEquals(
                cachedGifs.toTrendingGifsFromLocal(),
                viewModel.uiState.value.gifsData
            )
            Assert.assertEquals(false, viewModel.uiState.value.isLoading)
        }

    @Test
    fun `loadFavorites should update favoriteGifState with cached favorites`() = runTest {
        val favoriteGifs = FakeData.listOfTrendingGifs.map { it.toGifItem().toFavoriteGif() }
        coEvery { localGifsRepository.getFavoriteGifs() } returns flowOf(favoriteGifs)

        viewModel.loadFavoriteGif()
        advanceUntilIdle()

        Assert.assertEquals(favoriteGifs, viewModel.favoriteGifState.value.favoriteGifs)
        Assert.assertEquals(false, viewModel.favoriteGifState.value.isLoading)
    }

    @Test
    fun `monitorNetworkStatus should send snackbar event when disconnected`() = runTest {
        coEvery { networkMonitor.isConnected } returns MutableStateFlow(false)

        advanceUntilIdle()

        Assert.assertEquals(viewModel.state.value.isNoInternetConnection, false)
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
