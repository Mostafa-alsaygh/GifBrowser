package com.example.gifbrowserapp.presentation.features.giphyDetails

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.components.CustomButton
import com.example.gifbrowserapp.presentation.components.GifViewer
import com.example.gifbrowserapp.presentation.design.AppTheme

@Composable
fun GiphyDetailsScreen(
    viewModel: GiphyDetailsViewModel,
    navController: NavController,
    gifUrlOriginal: String?,
    gifUrl: String?
) {
    Column(Modifier.fillMaxSize()) {
        Text(text = gifUrl.let { Uri.decode(it) })
        GifViewer(
            gifUrlOriginal, modifier = Modifier
                .padding(AppTheme.sizes.medium)
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
        )

        HorizontalDivider(color = AppTheme.colors.primary, thickness = 2.dp)

        Row(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.sizes.medium),
        ) {

            CustomButton(buttonName = "Favorite", icon = R.drawable.ic_favorite, "", {})

            Spacer(modifier = Modifier.weight(0.3f))

            CustomButton(buttonName = "Share", icon = R.drawable.ic_share, "", {})

            Spacer(modifier = Modifier.weight(0.3f))

            OutlinedButton(
                onClick = {},
                content = { Text(text = "Copy Link") },
                border = BorderStroke(
                    width = 1.dp,
                    color = AppTheme.colors.primary
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppTheme.colors.primary
                ),
            )


        }
    }
}