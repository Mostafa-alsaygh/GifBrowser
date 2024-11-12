package com.example.gifbrowserapp.presentation.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

@Composable
fun <E> E.Listen(onEvent: suspend CoroutineScope.(currentEvent: E) -> Unit) {
    LaunchedEffect(key1 = this) {
        onEvent(this@Listen)
    }
}

@Composable
fun <E> E.Listen(filter: E.() -> Boolean = { true }, onEvent: suspend CoroutineScope.(currentEvent: E) -> Unit) {
    LaunchedEffect(key1 = this) {
        if (filter())
            onEvent(this@Listen)
    }
}
