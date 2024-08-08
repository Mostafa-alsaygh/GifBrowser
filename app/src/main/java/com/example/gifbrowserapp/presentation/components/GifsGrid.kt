package com.example.gifbrowserapp.presentation.components

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@Composable
fun <T> GifsGrid(
    modifier: Modifier = Modifier,
    gifList: List<T>,
    onGifClick: (String, String) -> Unit,
    extractOriginalGifUrl: (T) -> String,
    extractDownsampledUrl: (T) -> String,
    extractGifWebUrl: (T) -> String
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(gifList) { item ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(extractDownsampledUrl(item))
                        .apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                decoderFactory(ImageDecoderDecoder.Factory())
                            } else {
                                decoderFactory(GifDecoder.Factory())
                            }
                        }
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onGifClick(extractOriginalGifUrl(item), extractGifWebUrl(item)) },
                    contentScale = ContentScale.Crop
                )
            }
        },
        modifier = modifier
    )
}