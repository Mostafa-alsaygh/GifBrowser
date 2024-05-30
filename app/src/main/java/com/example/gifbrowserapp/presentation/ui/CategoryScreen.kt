package com.example.gifbrowserapp.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen() {

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        
    }
}


@Composable
@Preview(showBackground = true)
private fun Preview() = CategoryScreen()

