package com.example.gifbrowserapp.presentation.features.giphyDetails

import android.content.Intent

interface GiphyDetailsEvent {
    data class ShareGif(val shareIntent: Intent) : GiphyDetailsEvent
}