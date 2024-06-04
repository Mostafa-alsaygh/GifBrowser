package com.example.gifbrowserapp.data.entities.categories


import com.google.gson.annotations.SerializedName

data class Gif(
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String?,
    @SerializedName("bitly_url")
    val bitlyUrl: String?,
    @SerializedName("content_url")
    val contentUrl: String?,
    @SerializedName("create_datetime")
    val createDatetime: String?,
    @SerializedName("embed_url")
    val embedUrl: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("images")
    val images: CategoriesImages?,
    @SerializedName("import_datetime")
    val importDatetime: String?,
    @SerializedName("is_sticker")
    val isSticker: Int?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("source_post_url")
    val sourcePostUrl: String?,
    @SerializedName("source_tld")
    val sourceTld: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("trending_datetime")
    val trendingDatetime: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("update_datetime")
    val updateDatetime: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("username")
    val username: String?
)