package com.example.gifbrowserapp.data.entities.remote.gifData


import com.google.gson.annotations.SerializedName

data class GifData(
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: GifImages?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)