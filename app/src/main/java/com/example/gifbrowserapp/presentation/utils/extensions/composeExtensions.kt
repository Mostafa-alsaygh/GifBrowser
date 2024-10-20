package com.example.gifbrowserapp.presentation.utils.extensions

import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest

val @receiver:DrawableRes Int.painter: Painter
    @Composable
    get() = painterResource(id = this)


@Composable
fun imageRequester(): ImageRequest.Builder {
    return ImageRequest.Builder(LocalContext.current)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED).crossfade(true)
        .listener(
            onSuccess = { _, result ->
                Log.d("COIL", "GIF loaded from ${result.dataSource}")
            },
            onError = { _, throwable ->
                Log.e("COIL", "Error loading GIF: ${throwable.request.error}")
            }
        )
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                decoderFactory(ImageDecoderDecoder.Factory())
            } else {
                decoderFactory(GifDecoder.Factory())
            }
        }
}

fun Modifier.clickableNoRipple(
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = composed {
    this.clickable(
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        indication = indication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )
}

fun ClipboardManager.copy(text: String?) = text?.let { setText(AnnotatedString(text)) }
