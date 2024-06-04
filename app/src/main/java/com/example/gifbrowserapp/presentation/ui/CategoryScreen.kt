package com.example.gifbrowserapp.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gifbrowserapp.presentation.design.components.GifsGrid


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CategoryScreen(giphyList: List<GiphyItem> ) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        GifsGrid(gifList =giphyList )
    }
}


//@Composable
//@Preview(showBackground = true)
//private fun Preview() = CategoryScreen()

