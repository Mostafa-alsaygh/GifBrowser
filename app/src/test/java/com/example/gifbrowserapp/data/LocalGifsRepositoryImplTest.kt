package com.example.gifbrowserapp.data

import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.local.FavoriteGifDao
import com.example.gifbrowserapp.data.local.TrendingGifDao
import com.example.gifbrowserapp.data.repository.LocalGifsRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalGifsRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()

    private val favoriteGifDao: FavoriteGifDao = mockk(relaxed = true)
    private val trendingGifDao: TrendingGifDao = mockk(relaxed = true)

    private lateinit var localGifsRepository: LocalGifsRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        localGifsRepository = LocalGifsRepositoryImpl(
            favoriteGifDao = favoriteGifDao,
            trendingGifDao = trendingGifDao
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getFavoriteById should return the correct favorite GIF`() = runTest {
        // Arrange
        val gifId = "1"
        val mockFavoriteGif =
            FavoriteGif(id = gifId, originalGifUrl = "url", webGifUrl = "webUrl", date = 123456789L)
        coEvery { favoriteGifDao.getFavoriteGifById(gifId) } returns mockFavoriteGif

        // Act
        val result = localGifsRepository.getFavoriteById(gifId)

        // Assert
        assertEquals(mockFavoriteGif, result)
        coVerify { favoriteGifDao.getFavoriteGifById(gifId) }
    }

    @Test
    fun `getFavoriteGifs should return a flow of favorite GIFs`() = runTest {
        // Arrange
        val mockFavoriteGifs = listOf(
            FavoriteGif(
                id = "1",
                originalGifUrl = "url1",
                webGifUrl = "webUrl1",
                date = 123456789L
            ),
            FavoriteGif(id = "2", originalGifUrl = "url2", webGifUrl = "webUrl2", date = 987654321L)
        )
        coEvery { favoriteGifDao.getFavoriteGifOrderedByDate() } returns flowOf(mockFavoriteGifs)

        // Act
        val resultFlow = localGifsRepository.getFavoriteGifs()

        // Assert
        val resultList = resultFlow.toList()
        assertEquals(mockFavoriteGifs, resultList.first())
        coVerify { favoriteGifDao.getFavoriteGifOrderedByDate() }
    }

    @Test
    fun `addFavoriteGif should insert a favorite GIF into the database`() = runTest {
        // Arrange
        val favoriteGif =
            FavoriteGif(id = "1", originalGifUrl = "url", webGifUrl = "webUrl", date = 123456789L)

        // Act
        localGifsRepository.addFavoriteGif(favoriteGif)

        // Assert
        coVerify { favoriteGifDao.addFavoriteGif(favoriteGif) }
    }

    @Test
    fun `removeFavoriteGif should delete a favorite GIF from the database`() = runTest {
        // Arrange
        val favoriteGif =
            FavoriteGif(id = "1", originalGifUrl = "url", webGifUrl = "webUrl", date = 123456789L)

        // Act
        localGifsRepository.removeFavoriteGif(favoriteGif)

        // Assert
        coVerify { favoriteGifDao.removeFavoriteGif(favoriteGif) }
    }

    @Test
    fun `getTrendingGifs should return a flow of trending GIFs`() = runTest {
        // Arrange
        val mockTrendingGifs = RepositoryFakeData.getLocalMockTrendingGifs()
        coEvery { trendingGifDao.getLastTrendingGifs() } returns flowOf(mockTrendingGifs)

        // Act
        val resultFlow = localGifsRepository.getTrendingGifs()

        // Assert
        val resultList = resultFlow.toList()
        assertEquals(mockTrendingGifs, resultList.first())
        coVerify { trendingGifDao.getLastTrendingGifs() }
    }

    @Test
    fun `addTrendingGifs should insert trending GIFs into the database`() = runTest {
        // Arrange
        val trendingGifs = RepositoryFakeData.getLocalMockTrendingGifs()

        // Act
        localGifsRepository.addTrendingGifs(trendingGifs)

        // Assert
        coVerify { trendingGifDao.addLastTrendingGifs(trendingGifs) }
    }
}
