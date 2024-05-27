package com.example.gifbrowserapp.presentation.design

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


object Spacer {
    @Composable
    fun Default() = Spacer(modifier = Modifier)

    @Composable
    fun ExtraSmall(vertical: Boolean = true) = Spacer(
        modifier = Modifier.getSpacer(
            vertical = vertical,
            distance = AppTheme.sizes.extraSmall
        )
    )

    @Composable
    fun Small(vertical: Boolean = true) = Spacer(
        modifier = Modifier.getSpacer(
            vertical = vertical,
            distance = AppTheme.sizes.small
        )
    )

    @Composable
    fun Medium(vertical: Boolean = true) = Spacer(
        modifier = Modifier.getSpacer(
            vertical = vertical,
            distance = AppTheme.sizes.medium
        )
    )

    @Composable
    fun Large(vertical: Boolean = true) = Spacer(
        modifier = Modifier.getSpacer(
            vertical = vertical,
            distance = AppTheme.sizes.large
        )
    )

    @Composable
    fun ExtraLarge(vertical: Boolean = true) = Spacer(
        modifier = Modifier.getSpacer(
            vertical = vertical,
            distance = AppTheme.sizes.extraLarge
        )
    )

    @Composable
    fun CustomSpace(vertical: Boolean = true, distance: Dp = 0.dp) = Spacer(
        modifier = Modifier.getSpacer(
            vertical = vertical,
            distance = distance
        )
    )
}

private fun Modifier.getSpacer(vertical: Boolean = true, distance: Dp): Modifier = then(
    if (vertical)
        Modifier.height(distance)
    else
        Modifier.width(distance)
)
