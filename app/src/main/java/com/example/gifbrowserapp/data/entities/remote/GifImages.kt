package com.example.gifbrowserapp.data.entities.remote.gifData


import com.example.gifbrowserapp.data.entities.remote.FixedWidthDownsampled
import com.example.gifbrowserapp.data.entities.remote.Original
import com.google.gson.annotations.SerializedName

data class GifImages(
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: FixedWidthDownsampled?,
    @SerializedName("original")
    val original: Original?
)