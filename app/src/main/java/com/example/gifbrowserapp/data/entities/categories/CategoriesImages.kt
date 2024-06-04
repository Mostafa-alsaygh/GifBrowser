package com.example.gifbrowserapp.data.entities.categories


import com.example.gifbrowserapp.data.entities.Original
import com.google.gson.annotations.SerializedName

data class CategoriesImages(
    @SerializedName("downsized_medium")
    val downsizedMedium: DownsizedMedium?,
    @SerializedName("fixed_width")
    val fixedWidth: FixedWidth?,
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: FixedWidthDownsampled?,
    @SerializedName("original")
    val original: Original?,
    @SerializedName("preview_gif")
    val previewGif: PreviewGif?,

    )