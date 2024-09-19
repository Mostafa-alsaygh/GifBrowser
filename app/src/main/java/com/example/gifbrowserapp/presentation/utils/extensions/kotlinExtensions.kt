package com.example.gifbrowserapp.presentation.utils.extensions

import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.gifbrowserapp.data.entities.local.FavoriteGif
import com.example.gifbrowserapp.data.entities.local.GifImage
import com.example.gifbrowserapp.data.entities.local.GifItem
import com.example.gifbrowserapp.presentation.features.home.TrendingGif
import com.example.gifbrowserapp.presentation.features.search.SearchedGif


fun @receiver:ColorInt Int?.toColor() = this?.run { Color(this) }

fun TrendingGif.toGifItem(): GifItem {
    return GifItem(
        id = this.id,
        url = this.url,
        images = GifImage(
            original = this.images.original,
            fixedWidthDownsampled = this.images.fixedWidthDownsampled
        )
    )
}

fun SearchedGif.toGifItem(): GifItem {
    return GifItem(
        id = this.id,
        url = this.url,
        images = GifImage(
            original = this.images.original,
            fixedWidthDownsampled = this.images.fixedWidthDownsampled
        )
    )
}

fun FavoriteGif.toGifItem(): GifItem {
    return GifItem(
        id = this.id,
        url = this.webGifUrl,
        images = GifImage(
            original = this.originalGifUrl,
            fixedWidthDownsampled = emptyString()
        )
    )
}

fun GifItem.toFavoriteGif(): FavoriteGif {
    return FavoriteGif(
        id = this.id,
        originalGifUrl = this.images.original,
        webGifUrl = this.url,
        date = System.currentTimeMillis()
    )
}

fun <T> T.optionalComposable(
    shouldExecute: Boolean = true,
    content: @Composable (T) -> Unit,
): @Composable (() -> Unit)? =
    takeIf { shouldExecute && this != null }
        ?.let { { content(this) } }

fun optionalComposable(
    shouldExecute: Boolean = true,
    content: @Composable () -> Unit,
): @Composable (() -> Unit)? = shouldExecute.takeIf { it }?.let { { content() } }

private const val EMPTY_STRING = ""
fun emptyString() = EMPTY_STRING