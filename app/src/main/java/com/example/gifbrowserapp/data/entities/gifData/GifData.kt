package com.example.gifbrowserapp.data.entities.gifData


import com.google.gson.annotations.SerializedName

data class GifData(
    @SerializedName("analytics")
    val analytics: Analytics?,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: GifImages?,
    @SerializedName("import_datetime")
    val importDatetime: String?,
    @SerializedName("is_sticker")
    val isSticker: Int?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("trending_datetime")
    val trendingDatetime: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)