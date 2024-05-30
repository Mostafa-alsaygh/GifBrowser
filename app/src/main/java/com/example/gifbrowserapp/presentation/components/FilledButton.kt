package com.example.gifbrowserapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gifbrowserapp.presentation.design.AppTheme

@Composable
fun FilledButtonExample(category: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.padding(all = AppTheme.sizes.small),
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.primary)
    )
    {
        Text(category)
    }
}