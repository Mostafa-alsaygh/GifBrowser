package com.example.gifbrowserapp.presentation.components

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
import coil.decode.ImageDecoderDecoder
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.gifbrowserapp.presentation.features.home.TrendingGif

@Composable
fun GifsGrid(
    modifier: Modifier = Modifier,
    gifList: List<TrendingGif>
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(gifList) { item ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.images.fixedWidthDownsampled)
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
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        },
        modifier = modifier
    )
}
