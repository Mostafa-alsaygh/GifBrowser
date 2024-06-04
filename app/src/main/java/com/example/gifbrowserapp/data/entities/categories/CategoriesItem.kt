package com.example.gifbrowserapp.data.entities.categories


import com.google.gson.annotations.SerializedName

data class CategoriesItem(
    @SerializedName("gif")
    val gif: Gif?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("name_encoded")
    val nameEncoded: String?,
    @SerializedName("subcategories")
    val subcategories: List<Subcategory?>?
)