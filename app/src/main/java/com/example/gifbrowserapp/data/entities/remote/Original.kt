package com.example.gifbrowserapp.data.entities.remote


import com.google.gson.annotations.SerializedName

data class Original(
    @SerializedName("size")
    val size: String,
    @SerializedName("url")
    val url: String,
)