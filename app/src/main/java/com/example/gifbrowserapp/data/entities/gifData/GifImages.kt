package com.example.gifbrowserapp.data.entities.gifData


import com.example.gifbrowserapp.data.entities.FixedWidthDownsampled
import com.example.gifbrowserapp.data.entities.Original
import com.google.gson.annotations.SerializedName

data class GifImages(
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: FixedWidthDownsampled?,
    @SerializedName("original")
    val original: Original?
)