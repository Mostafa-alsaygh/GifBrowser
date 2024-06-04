package com.example.gifbrowserapp.data.entities.categories


import com.google.gson.annotations.SerializedName

data class Subcategory(
    @SerializedName("name")
    val name: String?,
    @SerializedName("name_encoded")
    val nameEncoded: String?
)