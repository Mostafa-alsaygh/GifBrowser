package com.example.gifbrowserapp.data.entities.gifData


import com.google.gson.annotations.SerializedName

data class Onload(
    @SerializedName("url")
    val urlOnload: String?
)