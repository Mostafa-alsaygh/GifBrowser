package com.example.gifbrowserapp.data.entities.remote.gifData


import com.google.gson.annotations.SerializedName

data class Onclick(
    @SerializedName("url")
    val url: String?
)