package com.example.gifbrowserapp.data.entities.remote.categories


import com.example.gifbrowserapp.data.entities.remote.Original
import com.example.gifbrowserapp.data.entities.remote.FixedWidth
import com.example.gifbrowserapp.data.entities.remote.FixedWidthDownsampled
import com.google.gson.annotations.SerializedName

data class CategoriesImages(
    @SerializedName("fixed_width")
    val fixedWidth: FixedWidth?,
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: FixedWidthDownsampled?,
    @SerializedName("original")
    val original: Original?,
    @SerializedName("preview_gif")
    val previewGif: PreviewGif?,
    )