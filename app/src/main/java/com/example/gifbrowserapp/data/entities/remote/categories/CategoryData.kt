package com.example.gifbrowserapp.data.entities.remote.categories

import com.example.gifbrowserapp.data.entities.remote.gifData.GifData
import com.google.gson.annotations.SerializedName

data class CategoryData(

    @SerializedName("name")
    val name: String?,
    val gif :GifData
)