package com.example.gifbrowserapp.presentation.components

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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gifbrowserapp.presentation.utils.extensions.imageRequester

@Composable
fun <T> GifsGrid(
    modifier: Modifier = Modifier,
    gifList: List<T> = emptyList(),
    onGifClick: (T) -> Unit,
    extractUrl: (T) -> String,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(gifList) { item ->
                AsyncImage(
                    model = imageRequester().data(extractUrl(item)).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onGifClick(item) },
                    contentScale = ContentScale.Crop
                )
            }
        },
        modifier = modifier
    )
}