package com.example.gifbrowserapp.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GifBrowserApp @Inject constructor() : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    private fun timberConfig() {

    }
}