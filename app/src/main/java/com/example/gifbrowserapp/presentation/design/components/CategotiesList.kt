package com.example.gifbrowserapp.presentation.design.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gifbrowserapp.presentation.design.AppTheme

@Composable
fun CategoriesGrid(categories: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(horizontal = AppTheme.sizes.small)
    ) {
        items(categories.size) { index ->
            CategoryItem(category = categories[index]) { }
            //TODO implement onclick scope
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() =
    CategoriesGrid(listOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5"))