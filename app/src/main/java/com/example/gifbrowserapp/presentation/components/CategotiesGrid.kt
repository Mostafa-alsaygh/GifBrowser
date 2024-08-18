package com.example.gifbrowserapp.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gifbrowserapp.data.entities.remote.categories.CategoryData
import com.example.gifbrowserapp.presentation.design.AppTheme
import com.example.gifbrowserapp.presentation.features.home.HomeViewModel

@Composable
fun CategoriesGrid(categories: List<CategoryData>, viewModel: HomeViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(horizontal = AppTheme.sizes.small)
    ) {
        items(categories.size) { index ->
            CategoryItem(nameOfCategory = categories[index].name.toString()) { categoryName ->
                    viewModel.onClickCategory(categoryName)
            }
        }
    }
}