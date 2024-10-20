package com.example.gifbrowserapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gifbrowserapp.presentation.design.AppTheme

@Composable
fun CategoryItem(nameOfCategory: String, getCategoryName: (String) -> Unit) {
    Button(
        onClick = { getCategoryName(nameOfCategory) },
        modifier = Modifier.padding(all = AppTheme.sizes.small),
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.primary)
    )
    {
        Text(nameOfCategory, style = AppTheme.typography.titleMedium)
    }
}